/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comcrafthd.render;

import javax.microedition.m3g.Appearance;
import javax.microedition.m3g.Material;
import javax.microedition.m3g.PolygonMode;

/**
 *
 * @author quead
 */
public final class BlockMaterialList {

    public static final int MAX_MATERIALS = 8;
    
    public final BlockMaterial[] materials = new BlockMaterial[MAX_MATERIALS];
    
    public final BlockMaterial standardMat = createTestMaterial(0);
    
    private BlockMaterial createTestMaterial(int idx) {
        if (materials[idx] != null) {
            throw new RuntimeException("Material exists: " + idx);
        }

        Material mat = new Material();
        mat.setColor(Material.EMISSIVE, 0xFF00FF);

        PolygonMode polMod = new PolygonMode();
        polMod.setCulling(PolygonMode.CULL_NONE);
        
        Appearance apr = new Appearance();
        apr.setMaterial(mat);
        apr.setPolygonMode(polMod);
        
        BlockMaterial blockMat = new BlockMaterial((byte) idx, apr);
        
        materials[idx] = blockMat;
        return blockMat;
    }
    
}
