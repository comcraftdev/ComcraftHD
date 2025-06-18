package comcrafthd.client;

import comcrafthd.*;
import java.io.*;
import javax.microedition.m3g.*;

public final class BlockMaterialDefinitions {

    public static final int MAX_MATERIALS = 4;

    public static final BlockMaterial[] materials = new BlockMaterial[MAX_MATERIALS];

    public static BlockMaterial standardMat;
    
    private static boolean initialized = false;
    private static boolean dirty = false;

    public static void initialize() {
        if (initialized && !dirty) {
            return;
        }
        
        // Clear existing materials if dirty
        if (dirty) {
            for (int i = 0; i < materials.length; i++) {
                materials[i] = null;
            }
            System.gc();
        }
        
        standardMat = createMaterial(0);
        
        initialized = true;
        dirty = false;
    }
    
    public static void setDirty() {
        dirty = true;
    }

    private static BlockMaterial createMaterial(int idx) {
        if (materials[idx] != null) {
            throw new RuntimeException("material exists: " + idx);
        }

        Material mat = new Material();
        mat.setVertexColorTrackingEnable(true);

        Appearance apr = new Appearance();
        apr.setMaterial(mat);

        if (ComcraftPrefs.instance.fogEnabled) {
            Fog fog = new Fog();
//            fog.setColor(0xFFFFFF);
            fog.setColor(ComcraftRenderer.SKY_COLOR);
            fog.setLinear(
                    ComcraftPrefs.instance.chunkRenderDistance * (Chunk.CHUNK_SIZE - 1),
                    ComcraftPrefs.instance.chunkRenderDistance * (Chunk.CHUNK_SIZE + 0));

            apr.setFog(fog);
        }

        try {
            Image2D image = (Image2D) Loader.load("/terrain.png")[0];

            Texture2D texture = new Texture2D(image);
            apr.setTexture(0, texture);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        BlockMaterial blockMat = new BlockMaterial((byte) idx, apr);

        materials[idx] = blockMat;
        return blockMat;
    }

    private BlockMaterialDefinitions() {
        // Prevent instantiation
    }

}
