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
public final class ChunkPartitionPool {

    private static final int MAX_POOL_SIZE = 64;
    
    private final ChunkPartition[] pool = new ChunkPartition[MAX_POOL_SIZE];
    private int poolCount = 0;
    
    public boolean put(ChunkPartition chunkPartition) {
        if (poolCount < MAX_POOL_SIZE) {
            pool[poolCount++] = chunkPartition;
            return true;
        }
        return false;
    }
    
    public ChunkPartition get() {
        if (poolCount > 0) {
            ChunkPartition partition = pool[--poolCount];
            partition.clear();
            pool[poolCount] = null;
            return partition;
        }
        return new ChunkPartition();
    }
    
    public void clear() {
        for (int n = MAX_POOL_SIZE - 1; n >= 0; --n) {
            pool[n] = null;
        }
        poolCount = 0;
    }
    
}
