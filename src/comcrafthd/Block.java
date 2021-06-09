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

    public final short fullId;

    public final IBlockRenderer blockRenderer;
    
    public Block(byte id, byte metaId, IBlockRenderer blockRenderer) {
        fullId = (short) ((metaId << BLOCK_META_SHIFT) | id);
        
        this.blockRenderer = blockRenderer;
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
