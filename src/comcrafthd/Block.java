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
public class Block {

    public static final int BLOCK_MAX_ID = 256;

    public static final short BLOCK_ID_MASK = (short) 0x00FF;
    public static final short BLOCK_META_MASK = (short) 0x0F00;
    public static final int BLOCK_META_SHIFT = 8;

    public final short fullId;

    public Block(byte id, byte metaId) {
        fullId = (short) ((metaId << BLOCK_META_SHIFT) | id);
    }

    public byte getId() {
        return (byte) (fullId & BLOCK_ID_MASK);
    }

    public byte getMetaId() {
        return (byte) ((fullId >> BLOCK_META_SHIFT) & BLOCK_META_MASK);
    }

}
