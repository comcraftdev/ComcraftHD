package comcrafthd.blocks;

import comcrafthd.*;

/**
 * Interface for block behaviors. Most blocks will not have behaviors,
 * only special blocks like doors, chests, falling blocks, etc.
 */
public interface BlockBehavior {
    
    /**
     * Called when the block is placed in the world
     * @param world The world
     * @param x Block X coordinate
     * @param y Block Y coordinate  
     * @param z Block Z coordinate
     * @param metadata Block metadata
     * @param placer The entity that placed the block (can be null)
     */
    void onBlockPlaced(ChunkWorld world, int x, int y, int z, int metadata, Entity placer);
    
    /**
     * Called when the block is about to be broken
     * @param world The world
     * @param x Block X coordinate
     * @param y Block Y coordinate
     * @param z Block Z coordinate
     * @param metadata Block metadata
     * @param breaker The entity breaking the block (can be null)
     * @return true to allow breaking, false to prevent
     */
    boolean onBlockBreak(ChunkWorld world, int x, int y, int z, int metadata, Entity breaker);
    
    /**
     * Called when the block is clicked/activated
     * @param world The world
     * @param x Block X coordinate
     * @param y Block Y coordinate
     * @param z Block Z coordinate
     * @param metadata Block metadata
     * @param clicker The entity clicking the block
     * @return true if the click was handled, false otherwise
     */
    boolean onBlockClick(ChunkWorld world, int x, int y, int z, int metadata, Entity clicker);
    
    /**
     * Called on random ticks for blocks that need updates (crops, leaves, etc)
     * @param world The world
     * @param x Block X coordinate
     * @param y Block Y coordinate
     * @param z Block Z coordinate
     * @param metadata Block metadata
     */
    void onRandomTick(ChunkWorld world, int x, int y, int z, int metadata);
    
    /**
     * Called when a neighboring block changes
     * @param world The world
     * @param x Block X coordinate
     * @param y Block Y coordinate
     * @param z Block Z coordinate
     * @param metadata Block metadata
     * @param neighborX Neighbor X coordinate
     * @param neighborY Neighbor Y coordinate
     * @param neighborZ Neighbor Z coordinate
     */
    void onNeighborChanged(ChunkWorld world, int x, int y, int z, int metadata, 
                          int neighborX, int neighborY, int neighborZ);
    
    /**
     * Check if this block can be placed at the given location
     * @param world The world
     * @param x Block X coordinate
     * @param y Block Y coordinate
     * @param z Block Z coordinate
     * @return true if the block can be placed
     */
    boolean canPlaceAt(ChunkWorld world, int x, int y, int z);
    
    /**
     * Get the items dropped when this block is broken
     * @param blockId The block ID
     * @param metadata Block metadata
     * @param fortune Fortune level of the tool used
     * @return Array of [itemId, count] pairs, or null for default drop
     */
    int[][] getDrops(int blockId, int metadata, int fortune);
    
    /**
     * Get the hardness of this block (time to break)
     * @param metadata Block metadata
     * @return Hardness value, higher = slower to break
     */
    float getHardness(int metadata);
    
    /**
     * Check if this block requires support (like torches, rails)
     * @return true if the block needs support from below
     */
    boolean requiresSupport();
}