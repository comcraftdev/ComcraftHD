package comcrafthd;

import comcrafthd.client.*;

public class Block {

    public static final int BLOCK_MAX_ID = 256;

    public static final short BLOCK_ID_MASK = (short) 0x00FF;
    public static final short BLOCK_META_MASK = (short) 0xFF00;
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

    public final byte id;

    public final IBlockRenderer blockRenderer;

    public final boolean occludesNeighbourFace = true;

    public Block(byte id, IBlockRenderer blockRenderer) {
        this.id = id;
        this.blockRenderer = blockRenderer;
    }

    public String toString() {
        return "Block(" + id + ")";
    }

    public static byte getId(short data) {
        return (byte) (data & BLOCK_ID_MASK);
    }

    public static int getIndex(short data) {
        return (data & BLOCK_ID_MASK); // Avoids unnecessary cast to byte
    }

    public static byte getMeta(short data) {
        return (byte) ((data >> BLOCK_META_SHIFT) & BLOCK_META_MASK);
    }

}
