package comcrafthd;

public final class BlockDefinitions {

    public static final int MAX_BLOCKS = 256;

    public static final Block[] blocks = new Block[MAX_BLOCKS];
    
    /*
     * Block renderer IDs
     */
    public static final int RENDERER_STANDARD = 0;

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

    public static void register(Block block) {
        if (blocks[block.id] != null) {
            throw new IllegalStateException("block " + block.id + " already exists");
        }
        blocks[block.id] = block;
    }
}
