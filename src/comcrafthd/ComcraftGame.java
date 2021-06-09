/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd;

import comcrafthd.render.Renderer;
import comcrafthd.midlets.ComcraftMIDPCanvas;
import comcrafthd.midlets.ComcraftMIDlet;

/**
 *
 * @author quead
 */
public final class ComcraftGame implements Runnable {

    public static ComcraftGame instance;

    public final ComcraftGameConfiguration gameConfiguration;

    public boolean gamePaused = false;
    public boolean gameStopped = false;

    private final Thread gameThread;

    private final ComcraftMIDlet comcraftMIDlet;
    private final ComcraftMIDPCanvas comcraftMIDPCanvas;

    public final BlockList blockList;
    public final ChunkPartitionPool chunkPartitionPool;
    public final ChunkGenerator chunkGenerator;
    public final Renderer renderer;
    public final ChunkList chunkList;

    public ComcraftGame(ComcraftGameConfiguration gameConfiguration, ComcraftMIDlet comcraftMIDlet, ComcraftMIDPCanvas comcraftCanvas) {
        instance = this;

        this.gameConfiguration = gameConfiguration;
        this.comcraftMIDlet = comcraftMIDlet;
        this.comcraftMIDPCanvas = comcraftCanvas;

        gameThread = new Thread();
        
        blockList = new BlockList();
        chunkPartitionPool = new ChunkPartitionPool();
        chunkGenerator = new ChunkGenerator();
        renderer = new Renderer();
        chunkList = new ChunkList();
    }

    private boolean gameStarted = false;

    public void start() {
        if (!gameStarted) {
            gameStarted = true;
            gameThread.start();
        }
    }

    public synchronized void stop() {
        gameStopped = true;
        gamePaused = false;
        notify();
    }

    public synchronized void pause() {
        gamePaused = true;
    }

    public synchronized void resume() {
        gamePaused = false;
        notify();
    }

    public void run() {
        initialize();

        while (!gameStopped) {
            synchronized (this) {
                while (gamePaused) {
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }

                if (gameStopped) {
                    break;
                }
            }

            tick();

            Thread.yield();
        }
    }

    private static final int DEFAULT_CHUNK_RADIUS = 5;
    
    private void initialize() {
        blockList.initialize();
        renderer.initialize();
        
        chunkList.loadAround(0, 0, DEFAULT_CHUNK_RADIUS);
        
        System.gc();
    }

    private void tick() {
        renderer.renderTick();
    }

}
