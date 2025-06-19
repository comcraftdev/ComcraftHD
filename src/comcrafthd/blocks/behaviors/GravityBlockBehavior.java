package comcrafthd.blocks.behaviors;

import comcrafthd.*;
import comcrafthd.blocks.*;
import comcrafthd.entities.*;

/**
 * Behavior for blocks affected by gravity (sand, gravel, etc).
 * When the block below is removed, these blocks will fall.
 */
public class GravityBlockBehavior implements BlockBehavior {
    
    public void onBlockPlaced(ChunkWorld world, int x, int y, int z, int metadata, Entity placer) {
        // Check if block should fall when placed
        scheduleUpdate(world, x, y, z);
    }
    
    public boolean onBlockBreak(ChunkWorld world, int x, int y, int z, int metadata, Entity breaker) {
        return true; // Always allow breaking
    }
    
    public boolean onBlockClick(ChunkWorld world, int x, int y, int z, int metadata, Entity clicker) {
        return false; // No special click behavior
    }
    
    public void onRandomTick(ChunkWorld world, int x, int y, int z, int metadata) {
        // Not used for gravity blocks
    }
    
    public void onNeighborChanged(ChunkWorld world, int x, int y, int z, int metadata, 
                                  int neighborX, int neighborY, int neighborZ) {
        // Check if we should fall when neighbor changes
        if (neighborY == y - 1) { // Block below changed
            scheduleUpdate(world, x, y, z);
        }
    }
    
    public boolean canPlaceAt(ChunkWorld world, int x, int y, int z) {
        return true; // Can be placed anywhere
    }
    
    public int[][] getDrops(int blockId, int metadata, int fortune) {
        return null; // Default drop (the block itself)
    }
    
    public float getHardness(int metadata) {
        // This should be handled by the block definition itself
        return 0.5f; // Default for sand/gravel
    }
    
    public boolean requiresSupport() {
        return false; // Doesn't require support in the traditional sense
    }
    
    /**
     * Schedule an update to check if this block should fall
     */
    private void scheduleUpdate(ChunkWorld world, int x, int y, int z) {
        // In a full implementation, this would schedule a tick update
        // For now, we'll check immediately
        checkFall(world, x, y, z);
    }
    
    /**
     * Check if this block should fall and spawn a falling entity if needed
     */
    private void checkFall(ChunkWorld world, int x, int y, int z) {
        if (canFallThrough(world, x, y - 1, z)) {
            // Get block data before removing
            short blockData = world.get(x, y, z);
            int blockId = Block.getId(blockData);
            int metadata = Block.getMeta(blockData);
            
            // Remove the block
            world.set(x, y, z, (short)0);
            
            // Spawn falling block entity
            if (ComcraftGame.instance != null) {
                FallingBlockEntity fallingBlock = new FallingBlockEntity(
                    ComcraftGame.instance, x + 0.5f, y, z + 0.5f, blockId, metadata
                );
                ComcraftGame.instance.addEntity(fallingBlock);
            }
        }
    }
    
    /**
     * Check if a block can fall through the given position
     */
    private boolean canFallThrough(ChunkWorld world, int x, int y, int z) {
        if (y < 0) {
            return false;
        }
        
        short blockData = world.get(x, y, z);
        int blockId = Block.getId(blockData);
        
        if (blockId == 0) { // Air
            return true;
        }
        
        Block block = BlockDefinitions.blocks[blockId];
        if (block == null) {
            return false;
        }
        
        // Can fall through liquids and non-solid materials
        return block.material == Block.MATERIAL_LIQUID ||
               block.material == Block.MATERIAL_PLANT ||
               !block.occludesNeighbourFace;
    }
}