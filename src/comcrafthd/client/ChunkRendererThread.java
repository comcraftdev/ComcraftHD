/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd.client;

import comcrafthd.Chunk;
import comcrafthd.ChunkList;
import comcrafthd.ComcraftGame;
import comcrafthd.Log;
import comcrafthd.MathHelper;

/**
 *
 * @author quead
 */
public final class ChunkRendererThread implements Runnable {

    private final ComcraftRenderer renderer;
    private final ChunkRenderer chunkRenderer;

    private boolean stopped = false;
    private Thread thread;

    public ChunkRendererThread(final ComcraftRenderer renderer, ChunkRenderer chunkRenderer) {
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

        final Chunk toRender = chunkList.getClosestNotRenderedChunk(centerBlockX, centerBlockZ);
        if (toRender != null) {
            chunkRenderer.renderChunkCache(toRender);
            
            renderer.threadCallbackAddChunk(toRender);
        }
    }

    public void dropChunkCallback(final Chunk chunk) {
        renderer.threadCallbackRemoveChunk(chunk);

        chunk.renderCache.done = false;
        chunk.renderCache.node = null;
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
