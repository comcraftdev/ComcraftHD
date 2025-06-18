package comcrafthd.client.blocks;

import comcrafthd.*;
import comcrafthd.client.*;

public class StandardBlockRenderer extends BlockRenderer {

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
    private static final byte S = ComcraftRenderer.BLOCK_RENDER_SIZE;

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

    /* */

    private final BlockMaterial blockMaterial;

    public StandardBlockRenderer(BlockMaterial blockMaterial) {
        this.blockMaterial = blockMaterial;
    }

    public void render(final ChunkRenderer chunkRenderer, final BlockRenderParam param) {
        final ChunkWorld chunkWorld = ComcraftGame.instance.chunkWorld;

        for (int side = 0; side < Block.MAX_SIDES; ++side) {
            if (!isSideOccluded(chunkWorld, param, Block.SIDE_OFFSETS[side])) {
                chunkRenderer.render(param, VERT[side], NORM[side], TEX[side], param.block.texX[side], param.block.texY[side], param.block.colors[side], STRIP_IND, STRIP_LEN, blockMaterial);
            }
        }
    }

    private boolean isSideOccluded(final ChunkWorld chunkWorld, final BlockRenderParam param, final int[] side) {
        final int x = param.blockX + side[0];
        final int y = param.blockY + side[1];
        final int z = param.blockZ + side[2];

        if (y < 0) {
            return true;
        }

        final short value = chunkWorld.get(x, y, z);
        final Block block = BlockDefinitions.blocks[Block.getIndex(value)];
        return block == null ? false : block.occludesNeighbourFace;
    }

}
