/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd.client.blocks;

import comcrafthd.Block;
import comcrafthd.client.BlockMaterial;
import comcrafthd.client.BlockRenderParam;
import comcrafthd.client.ChunkRenderer;
import comcrafthd.client.IBlockRenderer;
import comcrafthd.client.Renderer;

/**
 *
 * @author quead
 */
public class StandardBlockRenderer implements IBlockRenderer {

    public static final int SIDE_FRONT = 0;
    public static final int SIDE_BACK = 1;
    public static final int SIDE_LEFT = 2;
    public static final int SIDE_RIGHT = 3;
    public static final int SIDE_TOP = 4;
    public static final int SIDE_BOTTOM = 5;
    
    // In this case
    // Front is positive z axis
    // Back is negative z axis
    // Left is negative x axis
    // Right is positive x axis
    // Top is negative y axis
    // Bottom is positive y axis
    // Each line in this array declaration represents a triangle strip for
    // one side of a cube. The only primitive we can draw with is the
    // triangle strip so if we want to make a cube with hard edges we
    // need to construct one triangle strip per face of the cube.
    // 1 * * * * * 0
    // * * *
    // * * *
    // * * *
    // 3 * * * * * 2
    // The ascii diagram above represents the vertices in the first line
    // (the first tri-strip
    
    private static final byte BLOCK_SIZE = Renderer.BLOCK_RENDER_SIZE;
    
    public static final byte[][] VERT = {
        {BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, 0, BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, 0, BLOCK_SIZE, 0, 0, BLOCK_SIZE}, // front
        {0, BLOCK_SIZE, 0, BLOCK_SIZE, BLOCK_SIZE, 0, 0, 0, 0, BLOCK_SIZE, 0, 0}, // back
        {0, BLOCK_SIZE, BLOCK_SIZE, 0, BLOCK_SIZE, 0, 0, 0, BLOCK_SIZE, 0, 0, 0}, // left
        {BLOCK_SIZE, BLOCK_SIZE, 0, BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, 0, 0, BLOCK_SIZE, 0, BLOCK_SIZE}, // right
        {BLOCK_SIZE, BLOCK_SIZE, 0, 0, BLOCK_SIZE, 0, BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, 0, BLOCK_SIZE, BLOCK_SIZE}, // top
        {BLOCK_SIZE, 0, BLOCK_SIZE, 0, 0, BLOCK_SIZE, BLOCK_SIZE, 0, 0, 0, 0, 0} // bottom
    };

    public static final byte[][] NORM = {
        {0, 0, 127, 0, 0, 127, 0, 0, 127, 0, 0, 127},
        {0, 0, -127, 0, 0, -127, 0, 0, -127, 0, 0, -127},
        {-127, 0, 0, -127, 0, 0, -127, 0, 0, -127, 0, 0},
        {127, 0, 0, 127, 0, 0, 127, 0, 0, 127, 0, 0},
        {0, 127, 0, 0, 127, 0, 0, 127, 0, 0, 127, 0},
        {0, -127, 0, 0, -127, 0, 0, -127, 0, 0, -127, 0}
    };

    public static final byte[][] TEX = {
        {1, 0, 0, 0, 1, 1, 0, 1},
        {1, 0, 0, 0, 1, 1, 0, 1},
        {1, 0, 0, 0, 1, 1, 0, 1},
        {1, 0, 0, 0, 1, 1, 0, 1},
        {1, 0, 0, 0, 1, 1, 0, 1},
        {1, 0, 0, 0, 1, 1, 0, 1}};

    public static final int[] STRIP_IND = { 0, 1, 2, 3 };
    
    public static final int[] STRIP_LEN = {4};

    private final BlockMaterial blockMaterial;

    public final byte texX;
    public final byte texY;
    
    public StandardBlockRenderer(BlockMaterial blockMaterial, byte texX, byte texY) {
        this.blockMaterial = blockMaterial;
        this.texX = texX;
        this.texY = texY;
    }

    public void render(final ChunkRenderer chunkRenderer, final BlockRenderParam param) {
        for (int n = 0; n < 6; ++n) {
            chunkRenderer.render(param, VERT[n], NORM[n], TEX[n], texX, texY, STRIP_IND, STRIP_LEN, blockMaterial);
        }
    }

}