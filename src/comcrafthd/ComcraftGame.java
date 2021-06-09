/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd;

import comcrafthd.render.BlockMaterialList;
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
    public final BlockMaterialList blockMaterials;

    public ComcraftGame(ComcraftGameConfiguration gameConfiguration, ComcraftMIDlet comcraftMIDlet, ComcraftMIDPCanvas comcraftCanvas) {
        instance = this;

        this.gameConfiguration = gameConfiguration;
        this.comcraftMIDlet = comcraftMIDlet;
        this.comcraftMIDPCanvas = comcraftCanvas;

        gameThread = new Thread(this);
        
        blockMaterials = new BlockMaterialList();
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
        Log.info(this, "run() entered");
        
        initialize();

        while (!gameStopped) {
            synchronized (this) {
                while (gamePaused) {
                    Log.info(this, "run() gamePaused");
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }

                if (gameStopped) {
                    Log.info(this, "run() gameStopped break");
                    break;
                }
            }

            tick();

            Thread.yield();
        }
        
        Log.info(this, "run() finished");
    }

    private static final int DEFAULT_CHUNK_RADIUS = 2;
    
    private void initialize() {
        Log.info(this, "initialize() entered");
        
        blockList.initialize();
        renderer.initialize();
        
        chunkList.loadAround(0, 0, DEFAULT_CHUNK_RADIUS);
        
        System.gc();
        
        Log.info(this, "initialize() finished");
    }

    private void tick() {
//        Log.debug(this, "tick() entered");
        
        renderer.renderTick();
    }

}
