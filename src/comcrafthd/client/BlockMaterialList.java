package comcrafthd.client;

import comcrafthd.*;
import java.io.*;
import javax.microedition.m3g.*;

public final class BlockMaterialList {

    public static final int MAX_MATERIALS = 4;

    public final BlockMaterial[] materials = new BlockMaterial[MAX_MATERIALS];

    public final BlockMaterial standardMat = createTestMaterial(0);

    private BlockMaterial createTestMaterial(int idx) {
        if (materials[idx] != null) {
            throw new RuntimeException("Material exists: " + idx);
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

}
