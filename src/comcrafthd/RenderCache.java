package comcrafthd;

/**
 * Thread-safe render cache manager for components.
 * Provides synchronized access to render node and state.
 */
public final class RenderCache {

    private boolean done = false;
    private boolean cancelled = false;
    private Object node;

    public synchronized boolean isDone() {
        return done;
    }

    public synchronized void clear() {
        done = false;
        cancelled = false;
        node = null;
    }
    
    public synchronized void cancel() {
        cancelled = true;
        node = null;
    }

    public synchronized boolean set(Object node) {
        if (cancelled) {
            return false;
        }
        if (done) {
            throw new IllegalStateException("renderCache not empty");
        }
        this.node = node;
        done = true;
        return true;
    }

    public synchronized Object get() {
        return node;
    }
    
}
