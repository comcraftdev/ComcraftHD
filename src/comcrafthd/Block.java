package comcrafthd;

public class Block {

    public static final int BLOCK_MAX_ID = 256;

    /**
     * Bit masks for block data.
     * The first 8 bits are used for the block ID, and the next 4 bits are used for metadata.
     * The last 4 bits are reserved for lighting.
     */
    public static final short BLOCK_ID_MASK = (short) 0x00FF;
    public static final short BLOCK_META_MASK = (short) 0x0F;
    public static final int BLOCK_META_SHIFT = 8;

    public static final int MAX_SIDES = 6;

    public static final int SIDE_FRONT = 0;
    public static final int SIDE_BACK = 1;
    public static final int SIDE_LEFT = 2;
    public static final int SIDE_RIGHT = 3;
    public static final int SIDE_TOP = 4;
    public static final int SIDE_BOTTOM = 5;

    public static final int[][] SIDE_OFFSETS = {
        {0, 0, 1}, // front
        {0, 0, -1}, // back
        {-1, 0, 0}, // left
        {1, 0, 0}, // right
        {0, 1, 0}, // top
        {0, -1, 0} // bottom
    };

    /* */

    public final byte id;
    public final byte rendererIndex;

    public final byte[] texX;
    public final byte[] texY;
    public final byte[][] colors;

    public final boolean occludesNeighbourFace;
    public final boolean isTransparent;
    public final byte lightLevel;
    public final float hardness;
    public final byte material; // 0=solid, 1=liquid, 2=plant, 3=glass, etc
    public final String name;
    
    // Material type constants
    public static final byte MATERIAL_SOLID = 0;
    public static final byte MATERIAL_LIQUID = 1;
    public static final byte MATERIAL_PLANT = 2;
    public static final byte MATERIAL_GLASS = 3;
    public static final byte MATERIAL_LEAVES = 4;
    public static final byte MATERIAL_WOOL = 5;
    public static final byte MATERIAL_SAND = 6;
    public static final byte MATERIAL_WOOD = 7;
    public static final byte MATERIAL_STONE = 8;
    public static final byte MATERIAL_METAL = 9;

    public Block(byte id, byte rendererIndex, byte[] texX, byte[] texY, byte[][] colors,
                 boolean occludesNeighbourFace, boolean isTransparent, byte lightLevel,
                 float hardness, byte material, String name) {
        this.id = id;
        this.rendererIndex = rendererIndex;
        this.texX = texX;
        this.texY = texY;
        this.colors = colors;
        this.occludesNeighbourFace = occludesNeighbourFace;
        this.isTransparent = isTransparent;
        this.lightLevel = lightLevel;
        this.hardness = hardness;
        this.material = material;
        this.name = name;
    }
    
    // Backward compatibility constructor
    public Block(byte id, byte rendererIndex, byte[] texX, byte[] texY, byte[][] colors) {
        this(id, rendererIndex, texX, texY, colors, true, false, (byte)0, 1.0f, MATERIAL_SOLID, "block_" + id);
    }

    public String toString() {
        return "Block(" + id + ")";
    }

    public static int getId(short data) {
        return (data & BLOCK_ID_MASK);
    }

    public static int getMeta(short data) {
        return ((data >> BLOCK_META_SHIFT) & BLOCK_META_MASK);
    }

}
