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
public final class BlockList {
    
    public final BlockVariants[] blocks = new BlockVariants[Block.BLOCK_MAX_ID];
    
    public void initialize() {
        seedBlockList();
    }
    
    public Block get(byte id, byte meta) {
        BlockVariants variant = blocks[ByteHelper.toUnsignedByte(id)];
        
        if (variant.variantCount == 1) {
            return variant.variants[0];
        }
        
        return variant.variants[Block.getMetaIdFromMeta(meta)];
    }
    
    public void register(BlockVariants blockVariants) {
        int idx = ByteHelper.toUnsignedByte(blockVariants.id);
        
        if (blocks[idx] != null) {
            throw new RuntimeException("ID exists: " + idx);
        }
        
        blocks[idx] = blockVariants;
    }
    
    private void seedBlockList() {
        BlockCreator creator = new BlockCreator();
        
        stone = creator.createStandard(1, 0);
        
        creator.registerVariants(this);
    }
    
    public Block stone;
    
}
