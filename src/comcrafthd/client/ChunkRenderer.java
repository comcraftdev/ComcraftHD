/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd.client;

import comcrafthd.Block;
import comcrafthd.BlockList;
import comcrafthd.Chunk;
import comcrafthd.ComcraftGame;
import comcrafthd.Log;
import javax.microedition.m3g.Appearance;
import javax.microedition.m3g.Group;
import javax.microedition.m3g.Mesh;
import javax.microedition.m3g.Node;
import javax.microedition.m3g.TriangleStripArray;
import javax.microedition.m3g.VertexArray;
import javax.microedition.m3g.VertexBuffer;

/**
 *
 * @author quead
 */
public final class ChunkRenderer {

    private static final int MAX_VERTICES = 1024 * 16;
    private static final int MAX_STRIPS = MAX_VERTICES / 2;

    private final byte[] vertices = new byte[3 * MAX_VERTICES];
    private final byte[] normals = new byte[3 * MAX_VERTICES];
    private final byte[] texes = new byte[2 * MAX_VERTICES];
    private final byte[] colors = new byte[3 * MAX_VERTICES];
    private int vertCountX3;
    private int texCountX2;

    private final int[][] stripIndices = new int[BlockMaterialList.MAX_MATERIALS][MAX_STRIPS];
    private final int[][] stripLengths = new int[BlockMaterialList.MAX_MATERIALS][MAX_STRIPS];
    private final int[] stripsIndicesCount = new int[BlockMaterialList.MAX_MATERIALS];
    private final int[] stripsLengthsCount = new int[BlockMaterialList.MAX_MATERIALS];

    private Chunk chunk;

    private boolean overflow;

    private void clearCounts() {
        vertCountX3 = 0;
        texCountX2 = 0;
        overflow = false;

        for (int n = stripsIndicesCount.length - 1; n >= 0; --n) {
            stripsIndicesCount[n] = 0;
        }
        for (int n = stripsLengthsCount.length - 1; n >= 0; --n) {
            stripsLengthsCount[n] = 0;
        }
    }

    public Node renderChunk(final Chunk chunk) {
        clearCounts();

        this.chunk = chunk;

        renderChunkWork();

        Node node = prepareNode();

        this.chunk = null;
        return node;
    }

    private void renderChunkWork() {
        final Chunk chunk = this.chunk;
        final BlockList blockList = ComcraftGame.instance.blockList;

        final BlockRenderParam param = new BlockRenderParam();

        final int offsetX = chunk.chunkX << Chunk.BLOCK_TO_CHUNK_SHIFT;
        final int offsetZ = chunk.chunkZ << Chunk.BLOCK_TO_CHUNK_SHIFT;

        for (int y = 0; y < Chunk.CHUNK_HEIGHT; ++y) {
            param.blockY = y;
            param.localBlockY = y;
            for (int x = 0; x < Chunk.CHUNK_SIZE; ++x) {
                param.blockX = x + offsetX;
                param.localBlockX = x;
                for (int z = 0; z < Chunk.CHUNK_SIZE; ++z) {
                    param.blockZ = z + offsetZ;
                    param.localBlockZ = z;

                    final short val = chunk.get(x, y, z);

                    final byte id = Block.getId(val);
                    final byte meta = Block.getMeta(val);

                    final Block block = blockList.get(id, meta);
                    if (block == null) {
                        continue;
                    }

                    param.id = id;
                    param.meta = meta;
                    param.block = block;

                    block.blockRenderer.render(this, param);

                    if (overflow) {
                        return;
                    }
                }
            }
        }
    }

    private Node prepareNode() {
        Log.debug(this, "prepareNode() entered");

        if (vertCountX3 == 0) {
            Log.debug(this, "prepareNode() empty");
            return null;
        }

        final Chunk chunk = this.chunk;
        final BlockMaterialList materialList = ComcraftGame.instance.blockMaterials;

        final int vertCount = vertCountX3 / 3;

        VertexArray vertArr = new VertexArray(vertCount, 3, 1);
        vertArr.set(0, vertCount, vertices);

        VertexArray normArr = new VertexArray(vertCount, 3, 1);
        normArr.set(0, vertCount, normals);

        VertexArray texArr = new VertexArray(vertCount, 2, 1);
        texArr.set(0, vertCount, texes);

        VertexArray colArr = new VertexArray(vertCount, 3, 1);
        colArr.set(0, vertCount, colors);
        
        final float[] bias = {chunk.chunkX * Chunk.CHUNK_SIZE, 0, chunk.chunkZ * Chunk.CHUNK_SIZE};

        VertexBuffer vertexBuffer = new VertexBuffer();
        vertexBuffer.setPositions(vertArr, 1f / ComcraftRenderer.BLOCK_RENDER_SIZE, bias);
        vertexBuffer.setNormals(normArr);
        vertexBuffer.setTexCoords(0, texArr, 1f / ComcraftRenderer.TEXTURE_ATLAS_SIZE, null);
        vertexBuffer.setColors(colArr);

        int usedMaterialCount = 0;

        for (int n = BlockMaterialList.MAX_MATERIALS - 1; n >= 0; --n) {
            if (stripsIndicesCount[n] > 0) {
                usedMaterialCount++;
            }
        }

        final Appearance[] apprArr = new Appearance[usedMaterialCount];
        final TriangleStripArray[] stripsArrs = new TriangleStripArray[usedMaterialCount];
        int stripsArrsIdx = 0;

        for (int matIdx = BlockMaterialList.MAX_MATERIALS - 1; matIdx >= 0; --matIdx) {
            final int indicesCount = stripsIndicesCount[matIdx];
            final int lengthsCount = stripsLengthsCount[matIdx];

            if (indicesCount == 0) {
                continue;
            }

            final int[] tempStripIndices = new int[indicesCount];
            final int[] tempStripLengths = new int[lengthsCount];

            System.arraycopy(stripIndices[matIdx], 0, tempStripIndices, 0, indicesCount);
            System.arraycopy(stripLengths[matIdx], 0, tempStripLengths, 0, lengthsCount);

            TriangleStripArray triArr = new TriangleStripArray(tempStripIndices, tempStripLengths);
            stripsArrs[stripsArrsIdx] = triArr;

            apprArr[stripsArrsIdx] = materialList.materials[matIdx].appearance;

            ++stripsArrsIdx;
        }

        Log.debug(this, "prepareNode() finishing");

        Mesh mesh = new Mesh(vertexBuffer, stripsArrs, apprArr);
        return mesh;
    }

    public void render(
            final BlockRenderParam param,
            final byte[] vertices,
            final byte[] normals,
            final byte[] texes,
            final byte texOffsetX,
            final byte texOffsetY,
            final byte[] colors,
            final int[] stripIndices,
            final int[] stripLengths,
            final BlockMaterial material) {
        
        final int matIdx = material.id;

        if (vertices.length + vertCountX3 > MAX_VERTICES) {
            Log.debug(this, "render() max vert");
            overflow = true;
            return;
        }
        if (stripIndices.length + stripsIndicesCount[matIdx] > MAX_STRIPS) {
            Log.debug(this, "render() max indi");
            overflow = true;
            return;
        }

        final int startingVertIdx = vertCountX3;
        final int startingVertIdxForIndices = vertCountX3 / 3;

        final int vertLen = vertices.length;

        final int ox = (byte) (param.localBlockX * ComcraftRenderer.BLOCK_RENDER_SIZE);
        final int oy = (byte) (param.localBlockY * ComcraftRenderer.BLOCK_RENDER_SIZE);
        final int oz = (byte) (param.localBlockZ * ComcraftRenderer.BLOCK_RENDER_SIZE);

        for (int n = 0; n < vertLen; n += 3) {
            this.vertices[startingVertIdx + n + 0] = (byte) (vertices[n + 0] + ox);
            this.vertices[startingVertIdx + n + 1] = (byte) (vertices[n + 1] + oy);
            this.vertices[startingVertIdx + n + 2] = (byte) (vertices[n + 2] + oz);
        }

        System.arraycopy(normals, 0, this.normals, vertCountX3, vertLen);
        System.arraycopy(colors, 0, this.colors, vertCountX3, vertLen);
        vertCountX3 += vertLen;

        final int startingTexesIdx = texCountX2;
        final int texLen = texes.length;
        for (int n = 0; n < texLen; n += 2) {
            this.texes[startingTexesIdx + n + 0] = (byte) (texes[n + 0] + texOffsetX);
            this.texes[startingTexesIdx + n + 1] = (byte) (texes[n + 1] + texOffsetY);
        }
        texCountX2 += texLen;

        final int stripLen = stripIndices.length;

        final int startingIndicesIdx = stripsIndicesCount[matIdx];
        for (int n = 0; n < stripLen; ++n) {
            this.stripIndices[matIdx][startingIndicesIdx + n] = stripIndices[n] + startingVertIdxForIndices;
        }
        stripsIndicesCount[matIdx] += stripLen;

        System.arraycopy(stripLengths, 0, this.stripLengths[matIdx], stripsLengthsCount[matIdx], stripLengths.length);
        stripsLengthsCount[matIdx] += stripLengths.length;
    }

}
