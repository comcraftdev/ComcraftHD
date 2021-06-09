/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd.client;

import comcrafthd.Block;

/**
 *
 * @author quead
 */
public interface IBlockRenderer {
    
    public void render(final ChunkRenderer chunkRenderer, final BlockRenderParam param);
    
}
