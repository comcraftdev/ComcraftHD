package comcrafthd;

/**
 * Thread-safe render cache manager for components.
 * Provides synchronized access to render node and state.
 */
public final class RenderCache {

    private boolean done = false;
    private Object node;

    public synchronized boolean isDone() {
        return done;
    }

    public synchronized void clear() {
        done = false;
        node = null;
    }

    public synchronized void set(Object node) {
        this.node = node;
        done = true;
    }

    public synchronized Object get() {
        return node;
    }
    
}
