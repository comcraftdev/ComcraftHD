/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comcrafthd;

/**
 *
 * @author quead
 */
public final class ChunkList {

    public static final int MAX_CHUNKS = 256;
    
    public final Chunk[] chunks = new Chunk[MAX_CHUNKS];
    public int chunksSize = 0;
    
    public void loadAround(int blockX, int blockZ, int chunkRadius) {
        final short originChunkX = (short) (blockX >> Chunk.BLOCK_TO_CHUNK_SHIFT);
        final short originChunkZ = (short) (blockZ >> Chunk.BLOCK_TO_CHUNK_SHIFT);

        final int chunkRadiusSqr = chunkRadius * chunkRadius;
        
        for (int x = -chunkRadius; x <= chunkRadius; ++x) {
            for (int z = -chunkRadius; z <= chunkRadius; ++z) {
                if (x * x + z * z > chunkRadiusSqr) {
                    continue;
                }
                
                loadChunk((short) (originChunkX + x), (short) (originChunkZ + z));
            }
        }
    }
    
    public void loadChunk(short chunkX, short chunkZ) {
        Log.debug(this, "loadChunk() entered " + chunkX + ":" + chunkZ);
        
        if (chunkExists(chunkX, chunkZ)) {
            return;
        }
        
        if (chunksSize == MAX_CHUNKS) {
            Log.debug(this, "loadChunk() max chunks");
            return;
        }
        
        Chunk chunk = ComcraftGame.instance.chunkGenerator.generateChunk(chunkX, chunkZ);
        chunks[chunksSize++] = chunk;
        
        Log.debug(this, "loadChunk() finished " + chunk);
    }
    
    public boolean chunkExists(short chunkX, short chunkZ) {
        for (int n = chunksSize - 1; n >= 0; --n) {
            Chunk chunk = chunks[n];
            if (chunk != null && chunk.chunkX == chunkX && chunk.chunkZ == chunkZ) {
                return true;
            }
        }
        return false;
    }
    
}
