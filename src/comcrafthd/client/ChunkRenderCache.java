package comcrafthd.client;

import comcrafthd.*;
import javax.microedition.m3g.*;

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
