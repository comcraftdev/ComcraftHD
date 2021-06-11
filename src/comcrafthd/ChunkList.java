/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd;

import comcrafthd.client.ComcraftPrefs;
import comcrafthd.client.ComcraftRenderer;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author quead
 */
public final class ChunkList {

    public final Hashtable chunks = new Hashtable(getInitialSize());

    private static Integer getChunkKey(final int chunkX, final int chunkZ) {
        final short x = (short) chunkX;
        final short z = (short) chunkZ;
        return new Integer((x << 16) | (z & 0xFFFF));
    }

    private int getInitialSize() {
        return (int) (ComcraftPrefs.instance.chunkRenderDistance * ComcraftPrefs.instance.chunkRenderDistance * 3.2f) + 1;
    }

    public void triggerRenderChunks(final ComcraftRenderer renderer) {
        for (final Enumeration e = chunks.elements(); e.hasMoreElements();) {
            final Chunk chunk = (Chunk) e.nextElement();
            renderer.renderChunkListCallback(chunk);
        }
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
                dropChunk(chunk);
            }
        }
    }

    public void dropChunk(final Chunk chunk) {
        synchronized (chunk.renderCache) {
            if (chunk.renderCache.isCacheBeingGenerated) {
                return;
            }
        }
        
        chunks.remove(getChunkKey(chunk.chunkX, chunk.chunkZ));
        
        ComcraftGame.instance.renderer.dropChunkListCallback(chunk);
    }

    public void loadChunk(int chunkX, int chunkZ) {
//        Log.debug(this, "loadChunk() entered " + chunkX + ":" + chunkZ);

        if (chunkExists(chunkX, chunkZ)) {
            return;
        }

        Log.debug(this, "loadChunk() loading " + chunkX + ":" + chunkZ);

        Chunk chunk = ComcraftGame.instance.chunkGenerator.generateChunk(chunkX, chunkZ);

        addChunk(chunkX, chunkZ, chunk);

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

}
