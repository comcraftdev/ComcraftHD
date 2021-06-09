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
public final class ChunkGenerator {

    private Chunk chunk;
    
    public Chunk generateChunk(int chunkX, int chunkZ) {
        chunk = new Chunk((short) chunkX, (short) chunkZ);
        
        generateImpl();
        
        Chunk res = chunk;
        chunk = null;
        return res;
    }
    
    private void generateImpl() {
        BlockList blockList = ComcraftGame.instance.blockList;
        
        fillRect(0, 0, 0, Chunk.CHUNK_SIZE, 1, Chunk.CHUNK_SIZE, blockList.stone.fullId);
    }
    
    private void fillRect(int localBlockX, int localBlockY, int localBlockZ, int width, int height, int depth, short value) {
        for (int y = localBlockY; y < height; ++y) {
            for (int x = localBlockX; x < width; ++x) {
                for (int z = localBlockZ; z < depth; ++z) {
                    chunk.set(x, y, z, value);
                }
            }
        }
    }
    
}
