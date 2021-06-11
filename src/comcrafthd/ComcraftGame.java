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

        updateVisibleChunks();

        System.gc();

        Log.info(this, "initialize() finished");
    }

    public void tick() {
        cameraMovement.tick();

        updateVisibleChunks();
        
        renderer.render();
    }

    private void updateVisibleChunks() {
        final int centerBlockX = MathHelper.roundToInt(cameraMovement.positionX);
        final int centerBlockZ = MathHelper.roundToInt(cameraMovement.positionZ);
        
        chunkList.dropAround(centerBlockX, centerBlockZ, ComcraftPrefs.instance.chunkRenderDistance);
        chunkList.loadAround(centerBlockX, centerBlockZ, ComcraftPrefs.instance.chunkRenderDistance);
    }
    
    public void clear() {
        instance = null;
    }

}
