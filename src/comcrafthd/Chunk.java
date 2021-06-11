/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comcrafthd;

import comcrafthd.client.ChunkRenderCache;

/**
 *
 * @author quead
 */
public final class Chunk {
    
    public static final int CHUNK_SIZE = 8; 
    public static final int CHUNK_HEIGHT = 64;
    public static final int CHUNK_PARTITION_HEIGHT = 8;
    
    public static final int CHUNK_PARTITION_COUNT = CHUNK_HEIGHT / CHUNK_PARTITION_HEIGHT;

    public static final int BLOCK_TO_CHUNK_SHIFT = 3;
    public static final int BLOCK_TO_CHUNK_AND = 8 - 1;
    
    public static final int BLOCK_TO_PARTITION_SHIFT = 3;
    public static final int BLOCK_TO_PARTITION_AND = 8 - 1;
    
    public final ChunkPartition[] partitions = new ChunkPartition[CHUNK_PARTITION_COUNT];
    
    public final short chunkX;
    public final short chunkZ;
    
    public final ChunkRenderCache renderCache = new ChunkRenderCache();
    
    public Chunk(short chunkX, short chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }
    
    public static int blockToChunkCoord(final int blockCoord) {
        return blockCoord >> BLOCK_TO_CHUNK_SHIFT;
    }
    
    public void set(final int localBlockX, final int localBlockY, final int localBlockZ, final short value) {
        final int partitionY = (localBlockY >> Chunk.BLOCK_TO_PARTITION_SHIFT);
        final int localPartitionY = (localBlockY & Chunk.BLOCK_TO_PARTITION_AND);
        
        if (partitions[partitionY] == null) {
            if (value == 0) {
                return;
            }
            partitions[partitionY] = ComcraftGame.instance.chunkPartitionPool.get();
        }
        
        partitions[partitionY].set(localBlockX, localPartitionY, localBlockZ, value);
    }
    
    public short get(final int localBlockX, final int localBlockY, final int localBlockZ) {
        final int partitionY = (localBlockY >> Chunk.BLOCK_TO_PARTITION_SHIFT);
        final int localPartitionY = (localBlockY & Chunk.BLOCK_TO_PARTITION_AND);
        
        if (partitions[partitionY] == null) {
            return 0;
        }
        
        return partitions[partitionY].get(localBlockX, localPartitionY, localBlockZ);
    }
    
    public String toString() {
        return "Chunk(" + chunkX + ":" + chunkZ + ")";
    }
}
