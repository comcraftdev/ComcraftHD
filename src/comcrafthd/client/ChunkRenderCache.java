/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd.client;

import javax.microedition.m3g.Node;

/**
 *
 * @author quead
 */
public final class ChunkRenderCache {

    public boolean done = false;
    public Node node;

    public void clear() {
        done = false;
        node = null;
    }
        
}
