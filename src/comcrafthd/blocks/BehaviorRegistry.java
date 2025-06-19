package comcrafthd.blocks;

/**
 * Registry for block behaviors. Maps block IDs to their behaviors.
 * Most blocks have no behavior (null), only special blocks have behaviors.
 */
public class BehaviorRegistry {
    
    private static final BlockBehavior[] behaviors = new BlockBehavior[256];
    
    /**
     * Register a behavior for a block ID
     * @param blockId The block ID (0-255)
     * @param behavior The behavior, or null for no behavior
     */
    public static void register(int blockId, BlockBehavior behavior) {
        if (blockId < 0 || blockId >= behaviors.length) {
            throw new IllegalArgumentException("Invalid block ID: " + blockId);
        }
        behaviors[blockId] = behavior;
    }
    
    /**
     * Get the behavior for a block ID
     * @param blockId The block ID
     * @return The behavior, or null if no behavior
     */
    public static BlockBehavior getBehavior(int blockId) {
        if (blockId < 0 || blockId >= behaviors.length) {
            return null;
        }
        return behaviors[blockId];
    }
    
    /**
     * Check if a block has a behavior
     * @param blockId The block ID
     * @return true if the block has a behavior
     */
    public static boolean hasBehavior(int blockId) {
        return getBehavior(blockId) != null;
    }
    
    /**
     * Clear all registered behaviors (useful for testing)
     */
    public static void clear() {
        for (int i = 0; i < behaviors.length; i++) {
            behaviors[i] = null;
        }
    }
}