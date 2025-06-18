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
public final class BlockDefinitions {

    public static final int MAX_BLOCKS = 256;
    
    private static final Block[] allBlocks = new Block[MAX_BLOCKS];
    private static int allBlocksCount = 0;
    
    public static final Block stone = create(1)
            .setAllTexture(1, 0)
            .build();

    public static final Block grass = create(2)
            .setAllTexture(2, 0)
            .setSidesTexture(3, 0)
            .setTexture(Block.SIDE_TOP, 0, 0)
//            .setColor(Block.SIDE_TOP, 0x79C05A)
//            .setColor(Block.SIDE_TOP, 0x90814D)
            .setAllColor(0x79C05A)
//            .setAllColor(0x90814D)
            .build();
    
    private static BlockBuilder create(int id) {
        return BlockBuilder.create(id);
    }
    
    
    public static Block get(byte id) {
        return allBlocks[id];
    }

    public static void register(Block block) {
        if (allBlocksCount >= MAX_BLOCKS) {
            throw new IllegalStateException("BlockDefinitions register block");
        }
        
        allBlocks[block.getId()] = block;
        allBlocksCount++;
    }
}
