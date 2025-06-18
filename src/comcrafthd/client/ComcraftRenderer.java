package comcrafthd.client;

import comcrafthd.*;
import comcrafthd.client.midlets.*;
import java.io.*;
import javax.microedition.lcdui.*;
import javax.microedition.m3g.*;

public final class ComcraftRenderer {

    public static final byte BLOCK_RENDER_SIZE = 8;

    public static final int TEXTURE_ATLAS_SIZE = 16;

    public final ChunkRenderer chunkRenderer;
    public final ComcraftRendererThread rendererThread;

    private final GameCanvas canvas;
    private final Graphics graphics;
    private final Graphics3D g3d;

    private final World world = new World();

    public Camera camera;

    public ComcraftRenderer(GameCanvas canvas) {
        this.canvas = canvas;
        graphics = canvas.getGraphics();
        g3d = Graphics3D.getInstance();

        chunkRenderer = new ChunkRenderer();
        rendererThread = new ComcraftRendererThread(this, chunkRenderer);

        initializeWorld();
    }

    public void initialize() {
        rendererThread.start();
    }

    public void stop() {
        rendererThread.stop();
    }

    public synchronized void render() {
        int hints = Graphics3D.OVERWRITE;

        g3d.bindTarget(graphics, true, hints);
        g3d.clear(null);
        g3d.render(world);
        g3d.releaseTarget();

        canvas.flushGraphics();
    }

    public synchronized void threadCallbackAddChunk(final Chunk chunk) {
        ChunkRenderCache cache = (ChunkRenderCache) chunk.renderCache;
        world.addChild(cache.node);
    }

    public synchronized void threadCallbackRemoveChunk(final Chunk chunk) {
        ChunkRenderCache cache = (ChunkRenderCache) chunk.renderCache;
        world.removeChild(cache.node);
    }

    public static final int SKY_COLOR = 0x87ceeb;

    private void initializeWorld() {
        Log.info(this, "initializeWorld() entered");

        Background background = new Background();
        background.setColor(SKY_COLOR);
        world.setBackground(background);

        Light ambientLigth = new Light();
        ambientLigth.setMode(Light.AMBIENT);
        ambientLigth.setColor(0xffffff);
        ambientLigth.setIntensity(0.3f);

        world.addChild(ambientLigth);

        Light directionalLight = new Light();
        directionalLight.setMode(Light.DIRECTIONAL);
        directionalLight.setColor(0xffffff);
        directionalLight.setIntensity(1f);

        directionalLight.setOrientation(20f, 0, 1f, 0);
        directionalLight.postRotate(80f, -1f, 0, 0);

        world.addChild(directionalLight);

        camera = new Camera();
        camera.setPerspective(90.0f, // field of view
                (float) canvas.getWidth() / (float) canvas.getHeight(), // aspectRatio
                0.1f, // near clipping plane
                1000.0f); // far clipping plan

        camera.setTranslation(0, 10, 20);

        world.addChild(camera);
        world.setActiveCamera(camera);

        Log.info(this, "initializeWorld() finished");
    }

    private void addTestCube() {
        try {
            Node testCube = TestCube.getTestCube();
            world.addChild(testCube);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
