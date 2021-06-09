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
    
    public void register(BlockVariants blockVariants) {
        if (blocks[blockVariants.id] != null) {
            throw new RuntimeException("ID exists: " + blockVariants.id);
        }
        blocks[blockVariants.id] = blockVariants;
    }
    
    private void seedBlockList() {
        BlockCreator creator = new BlockCreator();
        
        stone = creator.createStandard(1, 0);
        
        creator.registerVariants(this);
    }
    
    public Block stone;
    
}
