/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd.client.blocks;

import comcrafthd.Block;
import comcrafthd.BlockList;
import comcrafthd.ChunkWorld;
import comcrafthd.ComcraftGame;
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
    // 1 * * * * * 0
    // * * *
    // * * *
    // * * *
    // 3 * * * * * 2
    // The ascii diagram above represents the vertices in the first line
    // (the first tri-strip)
    private static final byte S = Renderer.BLOCK_RENDER_SIZE;

    private static final byte[][] VERT = {
        {S, S, S, 0, S, S, S, 0, S, 0, 0, S}, // front
        {0, S, 0, S, S, 0, 0, 0, 0, S, 0, 0}, // back
        {0, S, S, 0, S, 0, 0, 0, S, 0, 0, 0}, // left
        {S, S, 0, S, S, S, S, 0, 0, S, 0, S}, // right
        {S, S, 0, 0, S, 0, S, S, S, 0, S, S}, // top
        {S, 0, S, 0, 0, S, S, 0, 0, 0, 0, 0} // bottom
    };

    private static final byte N = 127;

    private static final byte[][] NORM = {
        {0, 0, N, 0, 0, N, 0, 0, N, 0, 0, N},
        {0, 0, -N, 0, 0, -N, 0, 0, -N, 0, 0, -N},
        {-N, 0, 0, -N, 0, 0, -N, 0, 0, -N, 0, 0},
        {N, 0, 0, N, 0, 0, N, 0, 0, N, 0, 0},
        {0, N, 0, 0, N, 0, 0, N, 0, 0, N, 0},
        {0, -N, 0, 0, -N, 0, 0, -N, 0, 0, -N, 0}
    };

    private static final byte[][] TEX = {
        {1, 0, 0, 0, 1, 1, 0, 1},
        {1, 0, 0, 0, 1, 1, 0, 1},
        {1, 0, 0, 0, 1, 1, 0, 1},
        {1, 0, 0, 0, 1, 1, 0, 1},
        {1, 0, 0, 0, 1, 1, 0, 1},
        {1, 0, 0, 0, 1, 1, 0, 1}};

    private static final int[] STRIP_IND = {0, 1, 2, 3};

    private static final int[] STRIP_LEN = {4};

    private static final int[][] SIDE_OFFSETS = {
        {0, 0, 1}, // front
        {0, 0, -1}, // back
        {-1, 0, 0}, // left
        {1, 0, 0}, // right
        {0, 1, 0}, // top
        {0, -1, 0} // bottom
    };

    private final BlockMaterial blockMaterial;

    private final byte texX;
    private final byte texY;

    public StandardBlockRenderer(BlockMaterial blockMaterial, byte texX, byte texY) {
        this.blockMaterial = blockMaterial;
        this.texX = texX;
        this.texY = texY;
    }

    public void render(final ChunkRenderer chunkRenderer, final BlockRenderParam param) {
        final ChunkWorld chunkWorld = ComcraftGame.instance.chunkWorld;
        final BlockList blockList = ComcraftGame.instance.blockList;
        
        for (int side = 0; side < 6; ++side) {
            if (!isSideOccluded(chunkWorld, blockList, param, SIDE_OFFSETS[side])) {
                chunkRenderer.render(param, VERT[side], NORM[side], TEX[side], texX, texY, STRIP_IND, STRIP_LEN, blockMaterial);
            }
        }
    }
    
    private boolean isSideOccluded(final ChunkWorld chunkWorld, final BlockList blockList, final BlockRenderParam param, final int[] side) {
        final short value = chunkWorld.get(param.blockX + side[0], param.blockY + side[1], param.blockZ + side[2]);
        final Block block = blockList.get(value);
        return block == null ? false : block.occludesNeighbourFace;
    }

}
