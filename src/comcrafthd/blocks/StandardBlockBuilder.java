/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd.blocks;

import comcrafthd.Block;
import comcrafthd.ComcraftGame;
import comcrafthd.client.IBlockRenderer;
import comcrafthd.client.blocks.StandardBlockRenderer;

/**
 *
 * @author quead
 */
public final class StandardBlockBuilder {

    private int id;
    private int variant;

    private byte[] texX = new byte[Block.MAX_SIDES];
    private byte[] texY = new byte[Block.MAX_SIDES];

    private byte[][] colors;

    private StandardBlockBuilder() {
    }

    public static StandardBlockBuilder create(int id, int variant) {
        StandardBlockBuilder builder = new StandardBlockBuilder();
        builder.id = id;
        builder.variant = variant;
        return builder;
    }

    public StandardBlockBuilder setTexture(int side, int x, int y) {
        texX[side] = (byte) x;
        texY[side] = (byte) y;
        return this;
    }

    public StandardBlockBuilder setSidesTexture(int x, int y) {
        setTexture(Block.SIDE_BACK, x, y);
        setTexture(Block.SIDE_FRONT, x, y);
        setTexture(Block.SIDE_LEFT, x, y);
        setTexture(Block.SIDE_RIGHT, x, y);
        return this;
    }

    public StandardBlockBuilder setAllTexture(int x, int y) {
        setTexture(Block.SIDE_BACK, x, y);
        setTexture(Block.SIDE_FRONT, x, y);
        setTexture(Block.SIDE_LEFT, x, y);
        setTexture(Block.SIDE_RIGHT, x, y);
        setTexture(Block.SIDE_TOP, x, y);
        setTexture(Block.SIDE_BOTTOM, x, y);
        return this;
    }
    
    public StandardBlockBuilder setColor(int side, int col) {
        if (colors == null) {
            colors = StandardBlockRenderer.createDefaultColorArray();
        }
        
        final byte r = (byte) ((col >> (8 * 2)) & 0xFF);
        final byte g = (byte) ((col >> (8 * 1)) & 0xFF);
        final byte b = (byte) ((col >> (8 * 0)) & 0xFF);
        
        for (int n = 0; n < colors[side].length; n += 3) {
            colors[side][n + 0] = r;
            colors[side][n + 1] = g;
            colors[side][n + 2] = b;
        }
        
        return this;
    }
    
    public StandardBlockBuilder setAllColor(int col) {
        setColor(Block.SIDE_BACK, col);
        setColor(Block.SIDE_BOTTOM, col);
        setColor(Block.SIDE_FRONT, col);
        setColor(Block.SIDE_LEFT, col);
        setColor(Block.SIDE_RIGHT, col);
        setColor(Block.SIDE_TOP, col);
        return this;
    }

    public Block build() {
        IBlockRenderer blockRenderer = new StandardBlockRenderer(
                ComcraftGame.instance.blockMaterials.standardMat,
                texX,
                texY,
                colors == null ? StandardBlockRenderer.DEFAULT_COLORS : colors);

        Block block = new Block((byte) id, (byte) variant, blockRenderer);

        ComcraftGame.instance.blockList.registerBlock(block);
        return block;
    }

}
