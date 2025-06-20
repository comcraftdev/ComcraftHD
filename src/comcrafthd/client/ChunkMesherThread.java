package comcrafthd.client;

import comcrafthd.*;
import comcrafthd.util.*;

public final class ChunkMesherThread implements Runnable {

    private final ComcraftRenderer renderer;
    private final ChunkMesher chunkMesher;

    private boolean stopped = false;
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

        notify();

        try {
            thread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void tick() {
        final ChunkWorld chunkWorld = ComcraftGame.instance.chunkWorld;
        final CameraMovement cameraMovement = ComcraftGame.instance.cameraMovement;

        final int centerBlockX = MathHelper.roundToInt(cameraMovement.positionX);
        final int centerBlockZ = MathHelper.roundToInt(cameraMovement.positionZ);

        final Chunk chunkToRender = chunkWorld.getClosestNotRenderedChunk(centerBlockX, centerBlockZ);
        if (chunkToRender != null) {
            if (chunkMesher.meshChunk(chunkToRender)) {
                renderer.threadCallbackAddRenderCache(chunkToRender.renderCache);
            }
        }
    }


    public void run() {
        Log.info(this, "run() entered");

        int oomCntr = 0;

        while (!stopped) {
            try {
                tick();

                oomCntr = 0;

                Thread.yield();
            } catch (OutOfMemoryError oom) {
                if (++oomCntr > 3) {
                    throw oom;
                }

                oom.printStackTrace();

                ComcraftGame.instance.tidyUpMemory();
            }
        }

        Log.info(this, "run() finished");
    }

}
