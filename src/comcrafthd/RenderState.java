package comcrafthd;

/**
 * Minimal interface for render state information needed by core classes.
 * Allows core classes to check render status without knowing about specific renderer implementations.
 */
public interface RenderState {
    /**
     * Returns whether the rendering is complete.
     */
    boolean isRenderDone();
}