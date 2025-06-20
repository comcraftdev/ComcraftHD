package comcrafthd;

public final class ChunkPartitionPool {

    private static final int MAX_POOL_SIZE = 64;
    
    private static final ChunkPartition[] pool = new ChunkPartition[MAX_POOL_SIZE];
    private static int poolCount = 0;
    
    private ChunkPartitionPool() {
        // Private constructor to prevent instantiation
    }

    public static boolean put(ChunkPartition chunkPartition) {
        if (poolCount < MAX_POOL_SIZE) {
            pool[poolCount++] = chunkPartition;
            return true;
        }
        return false;
    }

    public static ChunkPartition get() {
        if (poolCount > 0) {
            ChunkPartition partition = pool[--poolCount];
            partition.clear();
            pool[poolCount] = null;
            return partition;
        }
        return new ChunkPartition();
    }

    public static void clear() {
        for (int n = MAX_POOL_SIZE - 1; n >= 0; --n) {
            pool[n] = null;
        }
        poolCount = 0;
    }

}
