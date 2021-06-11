/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd.client;

import comcrafthd.Chunk;
import comcrafthd.ComcraftGame;
import comcrafthd.Log;

/**
 *
 * @author quead
 */
public final class ChunkRendererThread implements Runnable {

    private final ChunkRenderer chunkRenderer;

    private Thread thread;
    private boolean stopped = false;

    private Chunk nextChunk;
    public boolean hasVaccancy = false;

    public ChunkRendererThread(ChunkRenderer chunkRenderer) {
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
    }

    public synchronized void enqueue(final Chunk chunk) {
        if (nextChunk == null) {

            synchronized (chunk.renderCache) {
                if (chunk.renderCache.isCacheBeingGenerated) {
                    return;
                }
                chunk.renderCache.isCacheBeingGenerated = true;
            }

            nextChunk = chunk;
            hasVaccancy = false;

            notify();
        }
    }

    private int oomCntr = 0;
    
    private void runImpl() {
        Log.info(this, "runImpl() entered");
        
        while (true) {
            Chunk chunk;

            synchronized (this) {
                if (stopped) {
                    break;
                }

                hasVaccancy = true;

                if (nextChunk == null) {
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }

                chunk = nextChunk;
                nextChunk = null;
            }

            if (chunk != null) {
                if (chunk.renderCache.done) {
                    throw new IllegalStateException("ChunkRendererThread chunk.renderCache.done == true");
                }

                processChunk(chunk);
            }

            oomCntr = 0;
            
            Thread.yield();
        }
        
        Log.info(this, "runImpl() finished");
    }

    public void run() {
        Log.info(this, "run() entered");

        while (!stopped) {
            try {
                runImpl();
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

    private void processChunk(final Chunk chunk) {
        try {
            chunkRenderer.renderChunkCache(chunk);

            ComcraftGame.instance.renderer.chunkRendererThreadCallback(chunk);
        } finally {
            synchronized (chunk.renderCache) {
                chunk.renderCache.isCacheBeingGenerated = false;
            }
        }
    }

}
