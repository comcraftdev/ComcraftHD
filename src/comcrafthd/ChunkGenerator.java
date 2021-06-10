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
        BlockSeedList blocks = ComcraftGame.instance.blockSeedList;

        fillRect(0, 0, 0, Chunk.CHUNK_SIZE, 3, Chunk.CHUNK_SIZE, blocks.stone.fullId);
        
        fillRect(0, 3, 0, Chunk.CHUNK_SIZE, 5, Chunk.CHUNK_SIZE, blocks.grass.fullId);
    }

    private void fillRect(final int localBlockX, final int localBlockY, final int localBlockZ, final int width, final int height, final int depth, final short value) {
        final Chunk chunk = this.chunk;
        
        for (int y = localBlockY; y < height; ++y) {
            for (int x = localBlockX; x < width; ++x) {
                for (int z = localBlockZ; z < depth; ++z) {
                    chunk.set(x, y, z, value);
                }
            }
        }
    }

}
