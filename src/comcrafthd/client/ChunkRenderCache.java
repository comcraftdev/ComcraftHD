/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd.client;

import comcrafthd.RenderState;
import javax.microedition.m3g.Node;

/**
 *
 * @author quead
 */
public final class ChunkRenderCache implements RenderState {

    public boolean done = false;
    public Node node;

    public void clear() {
        done = false;
        node = null;
    }
    
    public boolean isRenderDone() {
        return done;
    }
        
}
