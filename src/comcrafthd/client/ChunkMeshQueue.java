package comcrafthd.client;

import comcrafthd.Chunk;

/**
 * Thread-safe queue for coordinating chunk meshing between game and mesher threads.
 * Uses a single-slot design to avoid duplicate work.
 */
public final class ChunkMeshQueue {
    
    private Chunk nextChunkToMesh = null;
    
    public synchronized void setNext(Chunk chunk) {
        nextChunkToMesh = chunk;
        notify();
    }
    
    public synchronized Chunk getNext() throws InterruptedException {
        while (nextChunkToMesh == null) {
            wait();
        }
        Chunk chunk = nextChunkToMesh;
        nextChunkToMesh = null;
        return chunk;
    }
}