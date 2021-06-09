/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comcrafthd;

import comcrafthd.render.BlockMaterial;
import javax.microedition.m3g.Appearance;

/**
 *
 * @author quead
 */
public final class BlockMaterialList {

    public static final int MAX_MATERIALS = 8;
    
    public final BlockMaterial[] materials = new BlockMaterial[MAX_MATERIALS];
    
    public final BlockMaterial standardMat = createMaterial(0);
    
    private BlockMaterial createMaterial(int idx) {
        if (materials[idx] != null) {
            throw new RuntimeException("Material exists: " + idx);
        }
        
        Appearance apr = new Appearance();
        
        BlockMaterial mat = new BlockMaterial((byte) idx, apr);
        
        materials[idx] = mat;
        return mat;
    }
    
}
