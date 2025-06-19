package comcrafthd;

public final class ChunkWorld {

    private final ChunkList chunkList = ComcraftGame.instance.chunkList;

    public void set(final int blockX, final int blockY, final int blockZ, final short value) {
        if (blockY < 0 || blockY >= Chunk.CHUNK_HEIGHT) {
            return;
        }
        
        final int chunkX = blockX >> Chunk.BLOCK_TO_CHUNK_SHIFT;
        final int chunkZ = blockZ >> Chunk.BLOCK_TO_CHUNK_SHIFT;

        final Chunk chunk = chunkList.getChunk(chunkX, chunkZ);
        if (chunk != null) {
            final int localX = blockX & Chunk.BLOCK_TO_CHUNK_AND;
            final int localZ = blockZ & Chunk.BLOCK_TO_CHUNK_AND;

            chunk.set(localX, blockY, localZ, value);
        }
    }

    public short get(final int blockX, final int blockY, final int blockZ) {
        if (blockY < 0 || blockY > Chunk.CHUNK_HEIGHT) {
            return 0;
        }

        final int chunkX = blockX >> Chunk.BLOCK_TO_CHUNK_SHIFT;
        final int chunkZ = blockZ >> Chunk.BLOCK_TO_CHUNK_SHIFT;

        final Chunk chunk = chunkList.getChunk(chunkX, chunkZ);
        if (chunk != null) {
            final int localX = blockX & Chunk.BLOCK_TO_CHUNK_AND;
            final int localZ = blockZ & Chunk.BLOCK_TO_CHUNK_AND;

            return chunk.get(localX, blockY, localZ);
        }

        return 0;
    }

}
