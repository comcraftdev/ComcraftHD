/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd;

/**
 *
 * @author quead
 */
public final class BlockVariants {
    
    public final byte id;
    public final byte variantCount;
    
    public final Block[] variants;
    
    public BlockVariants(byte id, byte variantCount) {
        this.id = id;
        this.variantCount = variantCount;
        
        variants = new Block[variantCount];
    }
    
    public BlockVariants set(byte variantId, Block block) {
        if (variants[variantId] != null) {
            throw new RuntimeException("Variant exists: " + id + ":" + variantId);
        }
        
        variants[variantId] = block;
        
        return this;
    }
    
}
