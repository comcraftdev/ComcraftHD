package comcrafthd.client;

import comcrafthd.*;
import comcrafthd.client.blocks.*;

public final class BlockRendererDefinitions {

    public static final int MAX_RENDERERS = 16;
    
    public static final BlockRenderer[] renderers = new BlockRenderer[MAX_RENDERERS];
    
    private static boolean initialized = false;
    private static boolean dirty = false;

    public static void register(int rendererId, BlockRenderer renderer) {
        if (renderers[rendererId] != null) {
            throw new IllegalStateException("block renderer " + rendererId + " already exists");
        }
        renderers[rendererId] = renderer;
    }

    public static void initialize() {
        if (initialized && !dirty) {
            return;
        }
        
        // Clear existing renderers if dirty
        if (dirty) {
            for (int i = 0; i < renderers.length; i++) {
                renderers[i] = null;
            }
            System.gc();
        }
        
        // Register the standard block renderer at index 0
        register(0, new StandardBlockRenderer(BlockMaterialDefinitions.standardMat));
        
        initialized = true;
        dirty = false;
    }
    
    public static void setDirty() {
        dirty = true;
    }

}