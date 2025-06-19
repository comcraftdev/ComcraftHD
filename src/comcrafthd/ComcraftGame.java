package comcrafthd;

import comcrafthd.client.*;
import comcrafthd.blocks.*;
import javax.microedition.m3g.*;
import java.util.Vector;

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
    
    private final Vector entities = new Vector();
    private final Vector entitiesToAdd = new Vector();
    private long lastTickTime = 0;
    
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
        BlockDefinitions.initializeBehaviors();

        renderer.start();

        System.gc();

        Log.info(this, "initialize() finished");
    }

    public void stop() {
        renderer.stop();
    }

    public void tick() {
        // Calculate delta time
        long currentTime = System.currentTimeMillis();
        float dt = 0.016f; // Default to 60 FPS
        if (lastTickTime != 0) {
            dt = (currentTime - lastTickTime) / 1000.0f;
            if (dt > 0.1f) dt = 0.1f; // Cap delta time
        }
        lastTickTime = currentTime;
        
        cameraMovement.tick();
        handleBlockInteractions();
        updateEntities(dt);
        renderer.render();
    }
    
    private void handleBlockInteractions() {
        BlockPicker picker = renderer.blockPicker;
        
        // Block destruction
        if (Keyboard.isKeyPressedOnce(keyboardMapping.destroyBlock)) {
            if (picker.hasTarget) {
                // Get block ID before breaking
                int blockId = Block.getId(chunkWorld.get(picker.targetX, picker.targetY, picker.targetZ));
                
                // Trigger block break behavior
                BlockBehavior behavior = BehaviorRegistry.getBehavior(blockId);
                boolean canBreak = true;
                if (behavior != null) {
                    canBreak = behavior.onBlockBreak(chunkWorld, picker.targetX, picker.targetY, picker.targetZ, 0, null);
                }
                
                if (canBreak) {
                    Log.info(this, "Breaking block at " + picker.targetX + "," + picker.targetY + "," + picker.targetZ + " (ID: " + blockId + ")");
                    chunkWorld.set(picker.targetX, picker.targetY, picker.targetZ, (short)0);
                    updateChunkAt(picker.targetX, picker.targetY, picker.targetZ);
                    
                    // Notify neighbors
                    notifyNeighbors(picker.targetX, picker.targetY, picker.targetZ);
                }
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
                        
                        int blockId = playerInventory.getSelectedBlockId();
                        short blockData = (short)(blockId & 0xFF);
                        Log.info(this, "Placing block at " + placementPos[0] + "," + placementPos[1] + "," + placementPos[2] + " (ID: " + blockId + ")");
                        chunkWorld.set(placementPos[0], placementPos[1], placementPos[2], blockData);
                        updateChunkAt(placementPos[0], placementPos[1], placementPos[2]);
                        
                        // Trigger block placed behavior
                        BlockBehavior behavior = BehaviorRegistry.getBehavior(blockId);
                        if (behavior != null) {
                            behavior.onBlockPlaced(chunkWorld, placementPos[0], placementPos[1], placementPos[2], 0, null);
                        }
                        
                        // Notify neighbors
                        notifyNeighbors(placementPos[0], placementPos[1], placementPos[2]);
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
            // Remove old render cache from world if it exists
            if (chunk.renderCache != null) {
                renderer.threadCallbackRemoveChunk(chunk);
            }
            // Invalidate render cache to force re-render
            chunk.renderCache = null;
            Log.debug(this, "Updated chunk at " + chunkX + "," + chunkZ);
        }
        
        // Check if block is at chunk boundary and update neighboring chunks
        int localX = x & Chunk.BLOCK_TO_CHUNK_AND;
        int localZ = z & Chunk.BLOCK_TO_CHUNK_AND;
        
        // Update neighboring chunks if at boundaries
        if (localX == 0) {
            updateChunkOnly(chunkX - 1, chunkZ);
        } else if (localX == Chunk.CHUNK_SIZE - 1) {
            updateChunkOnly(chunkX + 1, chunkZ);
        }
        
        if (localZ == 0) {
            updateChunkOnly(chunkX, chunkZ - 1);
        } else if (localZ == Chunk.CHUNK_SIZE - 1) {
            updateChunkOnly(chunkX, chunkZ + 1);
        }
    }
    
    private void updateChunkOnly(int chunkX, int chunkZ) {
        Chunk chunk = chunkList.getChunk(chunkX, chunkZ);
        if (chunk != null) {
            if (chunk.renderCache != null) {
                renderer.threadCallbackRemoveChunk(chunk);
            }
            chunk.renderCache = null;
            Log.debug(this, "Updated neighboring chunk at " + chunkX + "," + chunkZ);
        }
    }

    public void clear() {
        instance = null;
    }

    public void tidyUpMemory() {
        System.gc();
    }
    
    /**
     * Notify neighboring blocks that a block has changed
     */
    private void notifyNeighbors(int x, int y, int z) {
        for (int i = 0; i < Block.SIDE_OFFSETS.length; i++) {
            int nx = x + Block.SIDE_OFFSETS[i][0];
            int ny = y + Block.SIDE_OFFSETS[i][1];
            int nz = z + Block.SIDE_OFFSETS[i][2];
            
            int neighborId = Block.getId(chunkWorld.get(nx, ny, nz));
            BlockBehavior behavior = BehaviorRegistry.getBehavior(neighborId);
            if (behavior != null) {
                behavior.onNeighborChanged(chunkWorld, nx, ny, nz, 0, x, y, z);
            }
        }
    }
    
    /**
     * Add an entity to the world
     */
    public void addEntity(Entity entity) {
        entitiesToAdd.addElement(entity);
    }
    
    /**
     * Update all entities
     */
    private void updateEntities(float dt) {
        // Add pending entities
        while (!entitiesToAdd.isEmpty()) {
            Entity entity = (Entity) entitiesToAdd.elementAt(0);
            entitiesToAdd.removeElementAt(0);
            entities.addElement(entity);
            entity.onAdded();
            
            // Add to render world
            if (entity.getNode() != null) {
                renderer.world.addChild(entity.getNode());
            }
        }
        
        // Update existing entities
        for (int i = entities.size() - 1; i >= 0; i--) {
            Entity entity = (Entity) entities.elementAt(i);
            entity.update(dt);
            
            // Remove dead entities
            if (entity.isDead()) {
                entities.removeElementAt(i);
                entity.onRemoved();
            }
        }
    }
    
    public ChunkWorld getWorld() {
        return chunkWorld;
    }

}
