/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd;

import comcrafthd.client.IBlockRenderer;

/**
 *
 * @author quead
 */
public class Block {

    public static final int BLOCK_MAX_ID = 256;

    public static final short BLOCK_ID_MASK = (short) 0x00FF;
    public static final short BLOCK_META_MASK = (short) 0xFF00;
    public static final int BLOCK_META_SHIFT = 8;
    
    public static final short BLOCK_METAID_MASK = (short) 0x000F;

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
    
    public final short fullId;

    public final IBlockRenderer blockRenderer;
    
    public final boolean occludesNeighbourFace = true;
    
    public Block(byte id, byte metaId, IBlockRenderer blockRenderer) {
        fullId = (short) ((metaId << BLOCK_META_SHIFT) | id);
        
        this.blockRenderer = blockRenderer;
    }
    
    public String toString() {
        return "Block(id:" + getId() + ", metaId:" + getMetaId() + ")";
    }

    public byte getId() {
        return getId(fullId);
    }

    public byte getMetaId() {
        return (byte) (getMeta(fullId) & BLOCK_METAID_MASK);
    }
    
    public static byte getId(short val) {
        return (byte) (val & BLOCK_ID_MASK);
    }
    
    public static byte getMeta(short val) {
        return (byte) ((val >> BLOCK_META_SHIFT) & BLOCK_META_MASK);
    }
    
    public static byte getMetaIdFromMeta(byte meta) {
        return (byte) (meta & BLOCK_METAID_MASK);
    }

}
