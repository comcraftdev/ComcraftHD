package comcrafthd;

import javax.microedition.m3g.*;

/**
 * Base class for all entities in the game.
 * Entities are dynamic objects that can move and update (mobs, falling blocks, items, etc).
 */
public abstract class Entity {
    
    protected ComcraftGame game;
    protected float x, y, z;
    protected float vx, vy, vz;
    protected float width, height;
    protected boolean dead = false;
    protected Node node; // JSR-184 3D node for rendering
    
    public Entity(ComcraftGame game, float x, float y, float z) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = 0.98f;
        this.height = 0.98f;
    }
    
    /**
     * Update the entity's logic
     * @param dt Delta time in seconds
     */
    public abstract void update(float dt);
    
    /**
     * Get the JSR-184 node for rendering
     */
    public Node getNode() {
        return node;
    }
    
    /**
     * Called when the entity is added to the world
     */
    public void onAdded() {
        // Override in subclasses if needed
    }
    
    /**
     * Called when the entity is removed from the world
     */
    public void onRemoved() {
        if (node != null && node.getParent() != null) {
            ((Group) node.getParent()).removeChild(node);
        }
    }
    
    /**
     * Mark this entity for removal
     */
    public void remove() {
        dead = true;
    }
    
    public boolean isDead() {
        return dead;
    }
    
    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }
    
    /**
     * Check collision with blocks
     */
    protected boolean isColliding(float nx, float ny, float nz) {
        // Simple AABB collision with blocks
        ChunkWorld world = game.getWorld();
        
        int minX = (int) Math.floor(nx - width / 2);
        int maxX = (int) Math.floor(nx + width / 2);
        int minY = (int) Math.floor(ny);
        int maxY = (int) Math.floor(ny + height);
        int minZ = (int) Math.floor(nz - width / 2);
        int maxZ = (int) Math.floor(nz + width / 2);
        
        for (int bx = minX; bx <= maxX; bx++) {
            for (int by = minY; by <= maxY; by++) {
                for (int bz = minZ; bz <= maxZ; bz++) {
                    int blockId = Block.getId(world.get(bx, by, bz));
                    if (blockId != 0) {
                        Block block = BlockDefinitions.blocks[blockId];
                        if (block != null && block.occludesNeighbourFace) {
                            return true;
                        }
                    }
                }
            }
        }
        
        return false;
    }
}