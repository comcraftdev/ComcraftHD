package comcrafthd;

public interface ChunkWorldListener {
    void onChunkLoaded(Chunk chunk);
    void onChunkUnloaded(Chunk chunk);
    void onChunkModified(Chunk chunk);
}