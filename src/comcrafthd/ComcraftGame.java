package comcrafthd;

import comcrafthd.client.*;
import javax.microedition.m3g.*;

public final class ComcraftGame {

    public static ComcraftGame instance;

    public final ComcraftGameConfiguration gameConfiguration;
    public final ComcraftRenderer renderer;

    public final ChunkPartitionPool chunkPartitionPool;
    public final ChunkGenerator chunkGenerator;
    public final ChunkList chunkList;
    public final ChunkWorld chunkWorld;
    public final KeyboardMapping keyboardMapping;
    public final CameraMovement cameraMovement;
    public final PlayerInventory playerInventory;
    
    public ComcraftGame(ComcraftGameConfiguration gameConfiguration, ComcraftRenderer renderer) {
        if (instance != null) {
            throw new IllegalStateException("ComcraftGame instance already exists");
        }

        instance = this;

        this.gameConfiguration = gameConfiguration;
        this.renderer = renderer;

        chunkPartitionPool = new ChunkPartitionPool();
        chunkGenerator = new ChunkGenerator();
        chunkList = new ChunkList();
        chunkWorld = new ChunkWorld();
        keyboardMapping = new KeyboardMapping();
        cameraMovement = new CameraMovement();
        playerInventory = new PlayerInventory();
    }

    public void initialize() {
        Log.info(this, "initialize() entered");

        BlockMaterialDefinitions.initialize();
        BlockRendererDefinitions.initialize();

        renderer.start();

        System.gc();

        Log.info(this, "initialize() finished");
    }

    public void stop() {
        renderer.stop();
    }

    public void tick() {
        cameraMovement.tick();
        handleBlockInteractions();
        renderer.render();
    }
    
    private void handleBlockInteractions() {
        BlockPicker picker = renderer.blockPicker;
        
        // Block destruction
        if (Keyboard.isKeyPressedOnce(keyboardMapping.destroyBlock)) {
            if (picker.hasTarget) {
                chunkWorld.set(picker.targetX, picker.targetY, picker.targetZ, (short)0);
                updateChunkAt(picker.targetX, picker.targetY, picker.targetZ);
            }
        }
        
        // Block placement
        if (Keyboard.isKeyPressedOnce(keyboardMapping.placeBlock)) {
            if (picker.hasTarget) {
                int[] placementPos = new int[3];
                picker.getPlacementPosition(placementPos);
                
                // Check if position is empty
                if (chunkWorld.get(placementPos[0], placementPos[1], placementPos[2]) == 0) {
                    // Check if not placing inside player
                    float playerX = cameraMovement.positionX;
                    float playerY = cameraMovement.positionY;
                    float playerZ = cameraMovement.positionZ;
                    
                    if (Math.abs(playerX - placementPos[0]) > 0.5f || 
                        Math.abs(playerY - placementPos[1]) > 0.5f ||
                        Math.abs(playerZ - placementPos[2]) > 0.5f) {
                        
                        short blockData = (short)(playerInventory.getSelectedBlockId() & 0xFF);
                        chunkWorld.set(placementPos[0], placementPos[1], placementPos[2], blockData);
                        updateChunkAt(placementPos[0], placementPos[1], placementPos[2]);
                    }
                }
            }
        }
        
        // Block selection
        if (Keyboard.isKeyPressedOnce(keyboardMapping.nextBlock)) {
            playerInventory.nextBlock();
        }
        if (Keyboard.isKeyPressedOnce(keyboardMapping.previousBlock)) {
            playerInventory.previousBlock();
        }
    }
    
    private void updateChunkAt(int x, int y, int z) {
        int chunkX = x >> Chunk.BLOCK_TO_CHUNK_SHIFT;
        int chunkY = y >> Chunk.BLOCK_TO_CHUNK_SHIFT;
        int chunkZ = z >> Chunk.BLOCK_TO_CHUNK_SHIFT;
        
        Chunk chunk = chunkList.getChunk(chunkX, chunkZ);
        if (chunk != null) {
            // Invalidate render cache to force re-render
            chunk.renderCache = null;
        }
    }

    public void clear() {
        instance = null;
    }

    public void tidyUpMemory() {
        System.gc();
    }

}
