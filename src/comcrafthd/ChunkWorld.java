package comcrafthd;

import comcrafthd.client.*;
import java.util.*;

public final class ChunkWorld {

    public final Hashtable chunks = new Hashtable(getInitialSize());
    
    private final ChunkGenerator chunkGenerator;
    private final ChunkWorldListener listener;

    private static Integer getChunkKey(final int chunkX, final int chunkZ) {
        final short x = (short) chunkX;
        final short z = (short) chunkZ;
        return new Integer((x << 16) | (z & 0xFFFF));
    }

    private int getInitialSize() {
        return (int) (ComcraftPrefs.instance.chunkRenderDistance * ComcraftPrefs.instance.chunkRenderDistance * 3.2f) + 1;
    }

    public ChunkWorld(ChunkGenerator chunkGenerator, ChunkWorldListener listener) {
        this.chunkGenerator = chunkGenerator;
        this.listener = listener;
    }

    public void set(final int blockX, final int blockY, final int blockZ, final short value) {
        if (blockY < 0 || blockY >= Chunk.CHUNK_HEIGHT) {
            return;
        }
        
        final int chunkX = blockX >> Chunk.BLOCK_TO_CHUNK_SHIFT;
        final int chunkZ = blockZ >> Chunk.BLOCK_TO_CHUNK_SHIFT;

        final Chunk chunk = getChunk(chunkX, chunkZ);
        if (chunk != null) {
            final int localX = blockX & Chunk.BLOCK_TO_CHUNK_AND;
            final int localZ = blockZ & Chunk.BLOCK_TO_CHUNK_AND;

            chunk.set(localX, blockY, localZ, value);
            invalidateChunkAt(blockX, blockY, blockZ);
        }
    }

    public short get(final int blockX, final int blockY, final int blockZ) {
        if (blockY < 0 || blockY >= Chunk.CHUNK_HEIGHT) {
            return 0;
        }

        final int chunkX = blockX >> Chunk.BLOCK_TO_CHUNK_SHIFT;
        final int chunkZ = blockZ >> Chunk.BLOCK_TO_CHUNK_SHIFT;

        final Chunk chunk = getChunk(chunkX, chunkZ);
        if (chunk != null) {
            final int localX = blockX & Chunk.BLOCK_TO_CHUNK_AND;
            final int localZ = blockZ & Chunk.BLOCK_TO_CHUNK_AND;

            return chunk.get(localX, blockY, localZ);
        }

        return 0;
    }

    public Chunk getClosestNotRenderedChunk(int blockX, int blockZ) {
        final short originChunkX = (short) (blockX >> Chunk.BLOCK_TO_CHUNK_SHIFT);
        final short originChunkZ = (short) (blockZ >> Chunk.BLOCK_TO_CHUNK_SHIFT);

        Chunk closest = null;
        int min = 0;

        for (final Enumeration e = chunks.elements(); e.hasMoreElements();) {
            final Chunk chunk = (Chunk) e.nextElement();

            if (chunk.renderCache.isDone()) {
                continue;
            }

            if (!areNeighboursLoaded(chunk.chunkX, chunk.chunkZ)) {
                continue;
            }

            final int x = chunk.chunkX - originChunkX;
            final int z = chunk.chunkZ - originChunkZ;

            final int dist = x * x + z * z;

            if (closest == null || dist < min) {
                closest = chunk;
                min = dist;
            }
        }

        return closest;
    }

    public void loadAround(int blockX, int blockZ, int chunkRadius) {
        final short originChunkX = (short) (blockX >> Chunk.BLOCK_TO_CHUNK_SHIFT);
        final short originChunkZ = (short) (blockZ >> Chunk.BLOCK_TO_CHUNK_SHIFT);

        final int chunkRadiusSqr = chunkRadius * chunkRadius;

        for (int x = -chunkRadius; x <= chunkRadius; ++x) {
            for (int z = -chunkRadius; z <= chunkRadius; ++z) {
                if (x * x + z * z > chunkRadiusSqr) {
                    continue;
                }

                loadChunk(originChunkX + x, originChunkZ + z);
            }
        }
    }

    public void dropAround(int blockX, int blockZ, int chunkRadius) {
        final short originChunkX = (short) (blockX >> Chunk.BLOCK_TO_CHUNK_SHIFT);
        final short originChunkZ = (short) (blockZ >> Chunk.BLOCK_TO_CHUNK_SHIFT);

        final int chunkRadiusSqr = chunkRadius * chunkRadius;

        for (final Enumeration e = chunks.elements(); e.hasMoreElements();) {
            final Chunk chunk = (Chunk) e.nextElement();

            final int x = chunk.chunkX - originChunkX;
            final int z = chunk.chunkZ - originChunkZ;

            if (x * x + z * z > chunkRadiusSqr) {
                chunks.remove(getChunkKey(chunk.chunkX, chunk.chunkZ));

                listener.onChunkUnloaded(chunk);
            }
        }
    }

    public void loadChunk(int chunkX, int chunkZ) {
//        Log.debug(this, "loadChunk() entered " + chunkX + ":" + chunkZ);

        if (chunkExists(chunkX, chunkZ)) {
            return;
        }

        Log.debug(this, "loadChunk() loading " + chunkX + ":" + chunkZ);

        Chunk chunk = chunkGenerator.generateChunk(chunkX, chunkZ);

        addChunk(chunkX, chunkZ, chunk);
        
        listener.onChunkLoaded(chunk);

        Log.debug(this, "loadChunk() finished " + chunk);
    }

    public boolean chunkExists(int chunkX, int chunkZ) {
        return chunks.containsKey(getChunkKey(chunkX, chunkZ));
    }

    public Chunk getChunk(int chunkX, int chunkZ) {
        return (Chunk) chunks.get(getChunkKey(chunkX, chunkZ));
    }

    private void addChunk(int chunkX, int chunkZ, Chunk chunk) {
        chunks.put(getChunkKey(chunkX, chunkZ), chunk);
    }

    public boolean areNeighboursLoaded(int chunkX, int chunkZ) {
        if (!chunkExists(chunkX - 1, chunkZ)) {
            return false;
        }
        if (!chunkExists(chunkX + 1, chunkZ)) {
            return false;
        }
        if (!chunkExists(chunkX, chunkZ - 1)) {
            return false;
        }
        if (!chunkExists(chunkX, chunkZ + 1)) {
            return false;
        }
        return true;
    }
    
    public void invalidateChunk(int chunkX, int chunkZ) {
        Chunk chunk = getChunk(chunkX, chunkZ);
        if (chunk != null) {
            listener.onChunkModified(chunk);
        }
    }
    
    public void invalidateChunkAt(int blockX, int blockY, int blockZ) {
        int chunkX = blockX >> Chunk.BLOCK_TO_CHUNK_SHIFT;
        int chunkZ = blockZ >> Chunk.BLOCK_TO_CHUNK_SHIFT;
        
        // Invalidate the chunk containing the block
        invalidateChunk(chunkX, chunkZ);
        
        // Check if block is at chunk boundary and invalidate neighboring chunks
        int localX = blockX & Chunk.BLOCK_TO_CHUNK_AND;
        int localZ = blockZ & Chunk.BLOCK_TO_CHUNK_AND;
        
        if (localX == 0) {
            invalidateChunk(chunkX - 1, chunkZ);
        } else if (localX == Chunk.CHUNK_SIZE - 1) {
            invalidateChunk(chunkX + 1, chunkZ);
        }
        
        if (localZ == 0) {
            invalidateChunk(chunkX, chunkZ - 1);
        } else if (localZ == Chunk.CHUNK_SIZE - 1) {
            invalidateChunk(chunkX, chunkZ + 1);
        }
    }

}