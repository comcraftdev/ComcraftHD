package comcrafthd.client;

import comcrafthd.*;
import javax.microedition.m3g.*;

public class BlockPicker {
    
    private final RayIntersection rayIntersection;
    
    // Current target state
    public int targetX, targetY, targetZ;
    public int targetFace;
    public boolean hasTarget;
    
    // Previous target for change detection
    private int prevTargetX = -1, prevTargetY = -1, prevTargetZ = -1;
    private boolean prevHasTarget = false;
    
    public BlockPicker() {
        this.rayIntersection = new RayIntersection();
    }
    
    public void updatePicking(World world, Camera camera) {
        hasTarget = false;
        
        // Use M3G pick() method on the world (0.5f, 0.5f = center of viewport)
        if (world.pick(-1, 0.5f, 0.5f, camera, rayIntersection)) {
            // Get the ray information
            float[] ray = new float[6]; // origin(3) + direction(3)
            rayIntersection.getRay(ray);
            
            // Get the distance to intersection
            float distance = rayIntersection.getDistance();
            
            // Calculate hit point: origin + direction * distance
            float hitX = ray[0] + ray[3] * distance;
            float hitY = ray[1] + ray[4] * distance;
            float hitZ = ray[2] + ray[5] * distance;
            
            // Get the face normal to determine which face was hit
            float normalX = rayIntersection.getNormalX();
            float normalY = rayIntersection.getNormalY();
            float normalZ = rayIntersection.getNormalZ();
            
            // Move slightly back along normal to ensure we're inside the block
            hitX -= normalX * 0.1f;
            hitY -= normalY * 0.1f;
            hitZ -= normalZ * 0.1f;
            
            // Convert from world units to block coordinates
            targetX = (int)Math.floor(hitX);
            targetY = (int)Math.floor(hitY);
            targetZ = (int)Math.floor(hitZ);
            
            // Determine face from normal
            determineFaceFromNormal(normalX, normalY, normalZ);
            
            hasTarget = true;
        }
        
        logTargetChanges();
    }
    
    private void determineFaceFromNormal(float normalX, float normalY, float normalZ) {
        // Find the dominant axis
        float absX = Math.abs(normalX);
        float absY = Math.abs(normalY);
        float absZ = Math.abs(normalZ);
        
        if (absX > absY && absX > absZ) {
            targetFace = normalX > 0 ? Block.SIDE_RIGHT : Block.SIDE_LEFT;
        } else if (absY > absZ) {
            targetFace = normalY > 0 ? Block.SIDE_TOP : Block.SIDE_BOTTOM;
        } else {
            targetFace = normalZ > 0 ? Block.SIDE_FRONT : Block.SIDE_BACK;
        }
    }
    
    public void getPlacementPosition(int[] result) {
        if (!hasTarget) {
            return;
        }
        
        int[] offset = Block.SIDE_OFFSETS[targetFace];
        result[0] = targetX + offset[0];
        result[1] = targetY + offset[1];
        result[2] = targetZ + offset[2];
    }
    
    private void logTargetChanges() {
        boolean targetChanged = false;
        
        // Check if target state changed
        if (hasTarget != prevHasTarget) {
            targetChanged = true;
        }
        
        // Check if target position changed
        if (hasTarget && prevHasTarget) {
            if (targetX != prevTargetX || targetY != prevTargetY || targetZ != prevTargetZ) {
                targetChanged = true;
            }
        }
        
        if (targetChanged) {
            if (hasTarget) {
                Log.debug(this, "Pick target changed to: " + targetX + "," + targetY + "," + targetZ + " (face: " + targetFace + ")");
            } else {
                Log.debug(this, "Pick target lost");
            }
            
            // Update previous values
            prevTargetX = targetX;
            prevTargetY = targetY;
            prevTargetZ = targetZ;
            prevHasTarget = hasTarget;
        }
    }
}