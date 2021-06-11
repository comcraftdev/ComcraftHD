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

    public final ChunkPartitionPool chunkPartitionPool;
    public final ChunkGenerator chunkGenerator;
    public final ChunkList chunkList;
    public final ChunkWorld chunkWorld;
    public final BlockList blockList;
    public final BlockSeedList blockSeedList;
    public final ComcraftRenderer renderer;
    public final BlockMaterialList blockMaterials;
    public final KeyboardMapping keyboardMapping;
    public final CameraMovement cameraMovement;

    public ComcraftGame(ComcraftGameConfiguration gameConfiguration) {
        if (instance != null) {
            throw new IllegalStateException("ComcraftGame");
        }

        instance = this;

        this.gameConfiguration = gameConfiguration;

        chunkPartitionPool = new ChunkPartitionPool();
        chunkGenerator = new ChunkGenerator();
        chunkList = new ChunkList();
        chunkWorld = new ChunkWorld();
        blockMaterials = new BlockMaterialList();
        blockList = new BlockList();
        blockSeedList = new BlockSeedList();
        renderer = new ComcraftRenderer();
        keyboardMapping = new KeyboardMapping();
        cameraMovement = new CameraMovement();
    }

    public void initialize() {
        Log.info(this, "initialize() entered");

        blockList.initialize();
        renderer.initialize();

        System.gc();

        Log.info(this, "initialize() finished");
    }
    
    public void stop() {
        renderer.stop();
    }

    public void tick() {
        cameraMovement.tick();

        renderer.render();
    }

    public void clear() {
        instance = null;
    }
    
    public void tidyUpMemory() {
        System.gc();
    }

}
