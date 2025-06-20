package comcrafthd.client;

import comcrafthd.*;
import comcrafthd.util.*;

public final class ComcraftRendererThread implements Runnable {

    private final ComcraftRenderer renderer;
    private final ChunkRenderer chunkRenderer;

    private boolean stopped = false;
    private Thread thread;

    public ComcraftRendererThread(final ComcraftRenderer renderer, ChunkRenderer chunkRenderer) {
        this.renderer = renderer;
        this.chunkRenderer = chunkRenderer;
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
            if (chunkRenderer.renderChunk(chunkToRender)) {
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
