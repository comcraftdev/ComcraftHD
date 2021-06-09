/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd.render;

import comcrafthd.Block;

/**
 *
 * @author quead
 */
public final class BlockRenderParam {

    public final ChunkRenderer chunkRenderer;
    
    public int blockX;
    public int blockY;
    public int blockZ;
    public int localBlockX;
    public int localBlockY;
    public int localBlockZ;
    public byte id;
    public byte meta;
    public Block block;
    
    public BlockRenderParam(ChunkRenderer chunkRenderer) {
        this.chunkRenderer = chunkRenderer;
    }

}
