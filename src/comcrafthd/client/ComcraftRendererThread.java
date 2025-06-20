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
        final ChunkList chunkList = ComcraftGame.instance.chunkList;
        final CameraMovement cameraMovement = ComcraftGame.instance.cameraMovement;

        final int centerBlockX = MathHelper.roundToInt(cameraMovement.positionX);
        final int centerBlockZ = MathHelper.roundToInt(cameraMovement.positionZ);

        chunkList.dropAround(centerBlockX, centerBlockZ, ComcraftPrefs.instance.chunkRenderDistance, this);
        chunkList.loadAround(centerBlockX, centerBlockZ, ComcraftPrefs.instance.chunkRenderDistance);

        final Chunk chunkToRender = chunkList.getClosestNotRenderedChunk(centerBlockX, centerBlockZ);
        if (chunkToRender != null) {
            chunkRenderer.renderChunk(chunkToRender);

            if (chunkToRender.renderCache.get() != null) {
                renderer.threadCallbackAddChunk(chunkToRender);
            }
        }
    }

    public void dropChunkCallback(final Chunk chunk) {
        if (chunk.renderCache.get() != null) {
            renderer.threadCallbackRemoveChunk(chunk);
        }

        chunk.renderCache.clear();
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
