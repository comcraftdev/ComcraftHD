/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd;

import comcrafthd.client.*;

/**
 *
 * @author quead
 */
public final class ComcraftGame {

    public static ComcraftGame instance;

    public final ComcraftGameConfiguration gameConfiguration;

    public final BlockList blockList;
    public final ChunkPartitionPool chunkPartitionPool;
    public final ChunkGenerator chunkGenerator;
    public final Renderer renderer;
    public final ChunkList chunkList;
    public final ChunkWorld chunkWorld;
    public final BlockMaterialList blockMaterials;
    public final KeyboardMapping keyboardMapping;
    public final CameraMovement cameraMovement;

    public ComcraftGame(ComcraftGameConfiguration gameConfiguration) {
        if (instance != null) {
            throw new IllegalStateException("ComcraftGame");
        }
        
        instance = this;

        this.gameConfiguration = gameConfiguration;

        blockMaterials = new BlockMaterialList();
        blockList = new BlockList();
        chunkPartitionPool = new ChunkPartitionPool();
        chunkGenerator = new ChunkGenerator();
        renderer = new Renderer();
        chunkList = new ChunkList();
        chunkWorld = new ChunkWorld();
        keyboardMapping = new KeyboardMapping();
        cameraMovement = new CameraMovement();
    }

    private static final int DEFAULT_CHUNK_RADIUS = 5;

    public void initialize() {
        Log.info(this, "initialize() entered");

        blockList.initialize();
        renderer.initialize();

        chunkList.loadAround(0, 0, DEFAULT_CHUNK_RADIUS);

        System.gc();

        Log.info(this, "initialize() finished");
    }

    public void tick() {
        cameraMovement.tick();

        renderer.render();
    }
    
    public void clear() {
        instance = null;
    }

}
