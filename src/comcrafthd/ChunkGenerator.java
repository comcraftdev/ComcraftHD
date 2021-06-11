/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd;

import make.some.noise.Noise;

/**
 *
 * @author quead
 */
public final class ChunkGenerator {

    private Chunk chunk;

    public Chunk generateChunk(int chunkX, int chunkZ) {
        chunk = new Chunk((short) chunkX, (short) chunkZ);

        Log.debug(this, "before generateImpl()");
        
        generateImpl();

        Log.debug(this, "after generateImpl()");
        
        Chunk res = chunk;
        chunk = null;
        return res;
    }

    private final Noise noise = Noise.instance;

    private void generateImpl() {
        final Chunk chunk = this.chunk;
        final BlockSeedList blocks = ComcraftGame.instance.blockSeedList;

        final int offsetX = chunk.chunkX * Chunk.CHUNK_SIZE;
        final int offsetZ = chunk.chunkZ * Chunk.CHUNK_SIZE;

//        final int offsetX = 0;
//        final int offsetZ = 0;
        for (int x = 0; x < Chunk.CHUNK_SIZE; ++x) {
            for (int z = 0; z < Chunk.CHUNK_SIZE; ++z) {
                final int globalX = offsetX + x;
                final int globalZ = offsetZ + z;

                final float noiseVal = noise.getPerlin(globalX, globalZ);
                final float height = 28 + noiseVal * 15;

                fillRect(x, 0, z, 1, (int) height, 1, blocks.grass.fullId);
            }
        }
    }

    private void generateImplTest() {
        final BlockSeedList blocks = ComcraftGame.instance.blockSeedList;

        fillRect(0, 0, 0, Chunk.CHUNK_SIZE, 3, Chunk.CHUNK_SIZE, blocks.stone.fullId);

        fillRect(0, 3, 0, Chunk.CHUNK_SIZE, 5, Chunk.CHUNK_SIZE, blocks.grass.fullId);
    }

    private void fillRect(final int localBlockX, final int localBlockY, final int localBlockZ, final int width, final int height, final int depth, final short value) {
        final Chunk chunk = this.chunk;

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                for (int z = 0; z < depth; ++z) {
                    chunk.set(localBlockX + x, localBlockY + y, localBlockZ + z, value);
                }
            }
        }
    }

}
