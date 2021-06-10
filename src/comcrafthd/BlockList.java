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

    public static final int MAX_BLOCKS = 512;
    
    public final BlockVariants[] variants = new BlockVariants[Block.BLOCK_MAX_ID];

    public final Block[] allBlocks = new Block[MAX_BLOCKS];
    public int allBlocksCount = 0;
    
    public void initialize() {
        initializeVariants();
    }

    public Block get(short value) {
        return get(Block.getId(value), Block.getMeta(value));
    }
    
    public Block get(byte id, byte meta) {
        BlockVariants variant = variants[ByteHelper.toUnsignedByte(id)];
        if (variant == null) {
            return null;
        }

        if (variant.variantCount == 1) {
            return variant.variants[0];
        }

        return variant.variants[Block.getMetaIdFromMeta(meta)];
    }

    public void registerBlock(Block block) {
        if (allBlocksCount >= MAX_BLOCKS) {
            throw new IllegalStateException("BlockList register block");
        }
        
        allBlocks[allBlocksCount++] = block;
    }
    
    public void registerVariant(BlockVariants blockVariants) {
        int idx = ByteHelper.toUnsignedByte(blockVariants.id);

        if (variants[idx] != null) {
            throw new RuntimeException("ID exists: " + idx);
        }

        variants[idx] = blockVariants;
    }

    private void initializeVariants() {
        int idx = 0;
        while (idx < allBlocksCount) {
            final byte startId = allBlocks[idx].getId();
            
            byte variantsCnt = 0;
            
            while (idx + variantsCnt < allBlocksCount && allBlocks[idx + variantsCnt].getId() == startId) {
                ++variantsCnt;
            }
            
            BlockVariants variant = new BlockVariants(startId, variantsCnt);
            
            for (byte n = 0; n < variantsCnt; ++n) {
                Block block = allBlocks[idx + n];
                
                if (block.getMetaId() != n) {
                    throw new IllegalStateException("initializeVariants() block: " + block);
                } 
                
                variant.set(n, allBlocks[idx + n]);
            }
            
            registerVariant(variant);
            
            idx += variantsCnt;
        }
    }
    
}
