/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd.render;

import comcrafthd.Chunk;
import comcrafthd.ChunkList;
import comcrafthd.ComcraftGame;
import javax.microedition.m3g.Node;
import javax.microedition.m3g.World;

/**
 *
 * @author quead
 */
public final class Renderer {
    
    public final ChunkRenderer chunkRenderer;
    
    public World world;
    
    public Renderer() {
        chunkRenderer = new ChunkRenderer();
    }
    
    public void initialize() {
        
    }
    
    public void renderTick() {
        renderChunksCache();
    }
    
    private void renderChunksCache() {
        ChunkList chunkList = ComcraftGame.instance.chunkList;
        
        for (int n = chunkList.chunksSize - 1; n >= 0; --n) {
            Chunk chunk = chunkList.chunks[n];
            
            if (chunk.renderCache == null) {
                renderChunkCache(chunk);
            }
        }
    }
    
    private void renderChunkCache(Chunk chunk) {
        if (chunk.renderCache.node != null) {
            world.removeChild(chunk.renderCache.node);
            chunk.renderCache.node = null;
        }
        
        Node node = chunkRenderer.render(chunk);
        chunk.renderCache.node = node;
        world.addChild(node);
    }
    
}
