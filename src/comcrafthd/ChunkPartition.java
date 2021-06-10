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
public final class ChunkPartition {

    public static final int PARTITION_LENGTH = Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE * Chunk.CHUNK_PARTITION_HEIGHT;
    
    private static final int Y_SHIFT = Chunk.BLOCK_TO_CHUNK_SHIFT * 2 ;
    private static final int X_SHIFT = Chunk.BLOCK_TO_CHUNK_SHIFT;
    
    public final short[] data = new short[PARTITION_LENGTH];
    
    public boolean isEmpty() {
        for (int n = PARTITION_LENGTH - 1; n >= 0; --n) {
            if (data[n] != 0) {
                return false;
            }
        }
        return true;
    }
    
    public void clear() {
        for (int n = PARTITION_LENGTH - 1; n >= 0; --n) {
            data[n] = 0;
        }
    }
    
    public void set(int localBlockX, int localBlockY, int localBlockZ, short value) {
        data[(localBlockY << Y_SHIFT) | (localBlockX << X_SHIFT) | localBlockZ] = value;
    }
    
    public short get(int localBlockX, int localBlockY, int localBlockZ) {
        return data[(localBlockY << Y_SHIFT) | (localBlockX << X_SHIFT) | localBlockZ];
    }
    
    public void test() {
        for (int y = 0; y < Chunk.CHUNK_PARTITION_HEIGHT; ++y) {
            for (int x = 0; x < Chunk.CHUNK_SIZE; ++x) {
                for (int z = 0; z < Chunk.CHUNK_SIZE; ++z) {
                    int a = (y << Y_SHIFT);
                    int b = (x << X_SHIFT);
                    int c = z;

                    TestHelper.validate((a & b) == 0);
                    TestHelper.validate((a & c) == 0);
                    TestHelper.validate((b & c) == 0);
                    
                    set(x, y, z, (byte) 1);
                }
            }
        }
        
        for (int n = PARTITION_LENGTH - 1; n >= 0; --n) {
            TestHelper.validate(data[n] == 1);
        }
    }
    
}
