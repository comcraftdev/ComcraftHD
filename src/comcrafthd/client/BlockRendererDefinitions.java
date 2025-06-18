package comcrafthd.client;

import comcrafthd.*;
import comcrafthd.client.blocks.*;

public final class BlockRendererDefinitions {

    public static final int MAX_RENDERERS = 16;
    
    public static final BlockRenderer[] renderers = new BlockRenderer[MAX_RENDERERS];

    public static void register(int rendererId, BlockRenderer renderer) {
        if (renderers[rendererId] != null) {
            throw new IllegalStateException("block renderer " + rendererId + " already exists");
        }
        renderers[rendererId] = renderer;
    }

    public static void initialize() {
        // Register the standard block renderer at index 0
        register(0, new StandardBlockRenderer(BlockMaterialDefinitions.standardMat));
    }

}