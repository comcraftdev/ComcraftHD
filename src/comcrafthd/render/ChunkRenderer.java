/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd.render;

import comcrafthd.Block;
import comcrafthd.BlockList;
import comcrafthd.BlockMaterialList;
import comcrafthd.Chunk;
import comcrafthd.ComcraftGame;
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
    private int vertCount;

    private final int[][] stripIndices = new int[BlockMaterialList.MAX_MATERIALS][MAX_STRIPS];
    private final int[][] stripLengths = new int[BlockMaterialList.MAX_MATERIALS][MAX_STRIPS];
    private final int[] stripsIndicesCount = new int[BlockMaterialList.MAX_MATERIALS];
    private final int[] stripsLengthsCount = new int[BlockMaterialList.MAX_MATERIALS];

    private Chunk chunk;

    public Node renderChunk(Chunk chunk) {
        clearCounts();

        this.chunk = chunk;

        renderChunkWork();

        Node node = prepareNode();
        chunk = null;
        return node;
    }

    private void renderChunkWork() {
        final Chunk chunk = this.chunk;
        final BlockList blockList = ComcraftGame.instance.blockList;

        final BlockRenderParam param = new BlockRenderParam(this);

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

                    param.id = id;
                    param.meta = meta;
                    param.block = block;

                    block.blockRenderer.render(param);
                }
            }
        }
    }

    private Node prepareNode() {
        final Chunk chunk = this.chunk;
        final BlockMaterialList materialList = ComcraftGame.instance.blockMaterials;

        VertexArray vertArr = new VertexArray(vertCount, 3, 1);
        vertArr.set(0, vertCount, vertices);

        VertexArray normArr = new VertexArray(vertCount, 3, 1);
        normArr.set(0, vertCount, normals);

        VertexArray texArr = new VertexArray(vertCount, 2, 1);
        texArr.set(0, vertCount, texes);

        final float[] bias = {chunk.chunkX * Chunk.CHUNK_SIZE, 0, chunk.chunkZ * Chunk.CHUNK_SIZE};

        VertexBuffer vertexBuffer = new VertexBuffer();
        vertexBuffer.setPositions(vertArr, 1f / Renderer.BLOCK_RENDER_SIZE, bias);
        vertexBuffer.setNormals(normArr);
        vertexBuffer.setTexCoords(0, texArr, 1f, null);

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
            
            System.arraycopy(stripIndices, 0, tempStripIndices, 0, indicesCount);
            System.arraycopy(stripLengths, 0, tempStripLengths, 0, lengthsCount);
            
            TriangleStripArray triArr = new TriangleStripArray(tempStripIndices, tempStripLengths);
            stripsArrs[stripsArrsIdx] = triArr;
            
            apprArr[stripsArrsIdx] = materialList.materials[matIdx].appearance;
            
            ++stripsArrsIdx;
        }
        
        Mesh mesh = new Mesh(vertexBuffer, stripsArrs, apprArr);
        return mesh;
    }

    private void clearCounts() {
        vertCount = 0;
        
        for (int n = stripsIndicesCount.length - 1; n >= 0; --n) {
            stripsIndicesCount[n] = 0;
        }
        for (int n = stripsLengthsCount.length - 1; n >= 0; --n) {
            stripsLengthsCount[n] = 0;
        }
    }

    public void render(byte[] vertices, byte[] normals, byte[] texes, int[] stripIndices, int[] stripLengths, BlockMaterial material) {
        final int matIdx = material.id;

        if (vertices.length + vertCount > MAX_VERTICES) {
            return;
        }
        if (stripIndices.length + stripsIndicesCount[matIdx] > MAX_STRIPS) {
            return;
        }

        final int startingVertIdx = vertCount;
        
        final int vertLen = vertices.length;
        System.arraycopy(vertices, 0, this.vertices, vertCount, vertLen);
        System.arraycopy(normals, 0, this.normals, vertCount, vertLen);
        System.arraycopy(texes, 0, this.texes, vertCount, vertLen);
        vertCount += vertLen;

        final int stripLen = stripIndices.length;
        
        final int startingIndicesIdx = stripsIndicesCount[matIdx];
        for (int n = 0; n < stripLen; ++n) {
            this.stripIndices[matIdx][startingIndicesIdx + n] = stripIndices[n] + startingVertIdx;
        }
        stripsIndicesCount[matIdx] += stripLen;
        
        System.arraycopy(stripLengths, 0, this.stripLengths, stripsLengthsCount[matIdx], stripLengths.length);
        stripsLengthsCount[matIdx] += stripLengths.length;
    }

}
