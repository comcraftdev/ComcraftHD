package comcrafthd.client;

import comcrafthd.*;

public final class ChunkMesherThread implements Runnable {

    private final ComcraftRenderer renderer;
    private final ChunkMesher chunkMesher;

    private volatile boolean stopped = false;
    private Thread thread;

    public ChunkMesherThread(final ComcraftRenderer renderer, ChunkMesher chunkMesher) {
        this.renderer = renderer;
        this.chunkMesher = chunkMesher;
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public synchronized void stop() {
        stopped = true;
        thread.interrupt(); // Wake up the thread
        try {
            thread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    private void tick() throws InterruptedException {
        Chunk chunkToMesh = renderer.getNextChunkToMesh();
        if (chunkToMesh != null && !chunkToMesh.renderCache.isDone()) {
            long startTime = System.currentTimeMillis();
            Log.debug(this, "Meshing chunk at " + chunkToMesh.chunkX + "," + chunkToMesh.chunkZ);
            
            if (chunkMesher.meshChunk(chunkToMesh)) {
                long meshTime = System.currentTimeMillis() - startTime;
                Log.debug(this, "Meshed chunk at " + chunkToMesh.chunkX + "," + chunkToMesh.chunkZ + " in " + meshTime + "ms");
                renderer.threadCallbackAddRenderCache(chunkToMesh.renderCache);
            } else {
                Log.debug(this, "Chunk at " + chunkToMesh.chunkX + "," + chunkToMesh.chunkZ + " was cancelled during meshing");
            }
        }
    }

    public void run() {
        Log.info(this, "Thread started");

        int oomCntr = 0;

        while (!stopped) {
            try {
                tick();
                oomCntr = 0;
            } catch (InterruptedException ie) {
                // Thread was interrupted, check if we should stop
                continue;
            } catch (OutOfMemoryError oom) {
                Log.info(this, "OutOfMemoryError during chunk meshing, counting: " + oomCntr);

                if (++oomCntr > 3) {
                    throw oom;
                }

                oom.printStackTrace();

                ComcraftGame.instance.tidyUpMemory();
            }
        }

        Log.info(this, "Thread stopped");
    }

}
