package comcrafthd.client;

import comcrafthd.*;
import comcrafthd.client.midlets.*;
import java.io.*;
import javax.microedition.lcdui.*;
import javax.microedition.m3g.*;

public final class ComcraftRenderer {

    public final ChunkRenderer chunkRenderer;
    public final ComcraftRendererThread rendererThread;

    public final GameCanvas canvas;
    private final Graphics graphics;
    private final Graphics3D g3d;

    public final World world = new World();

    public Camera camera;
    private SelectionRenderer selectionRenderer;
    public BlockPicker blockPicker;

    public ComcraftRenderer(GameCanvas canvas) {
        this.canvas = canvas;
        graphics = canvas.getGraphics();
        g3d = Graphics3D.getInstance();

        chunkRenderer = new ChunkRenderer();
        rendererThread = new ComcraftRendererThread(this, chunkRenderer);
        selectionRenderer = new SelectionRenderer();
        blockPicker = new BlockPicker();

        initializeWorld();
    }

    public void start() {
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
        
        // World raycasting need to run on the render thread
        blockPicker.updatePicking(world, camera);
        
        // Render selection box
        if (blockPicker.hasTarget) {
            selectionRenderer.render(g3d, blockPicker);
        }
        
        g3d.releaseTarget();
        
        // Draw HUD
        drawHUD(graphics);

        canvas.flushGraphics();
    }

    public synchronized void threadCallbackAddRenderCache(final RenderCache renderCache) {
        Node node = (Node) renderCache.get();
        if (node != null) {
            world.addChild(node);
        }
    }

    public synchronized void threadCallbackRemoveRenderCache(final RenderCache renderCache) {
        Node node = (Node) renderCache.get();
        if (node != null) {
            world.removeChild(node);
        }
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
    
    private void drawHUD(Graphics g) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        
        // Draw crosshair
        g.setColor(0xFFFFFF);
        int centerX = width / 2;
        int centerY = height / 2;
        int crosshairSize = 10;
        
        // Horizontal line
        g.drawLine(centerX - crosshairSize, centerY, centerX + crosshairSize, centerY);
        // Vertical line
        g.drawLine(centerX, centerY - crosshairSize, centerX, centerY + crosshairSize);
        
        // Draw selected block info
        if (ComcraftGame.instance != null && ComcraftGame.instance.playerInventory != null) {
            String blockName = ComcraftGame.instance.playerInventory.getSelectedBlockName();
            g.setColor(0xFFFFFF);
            g.drawString(blockName, 5, 5, Graphics.TOP | Graphics.LEFT);
        }
        
        // Draw controls hint
        g.setColor(0xFFFFFF);
        g.drawString("7:Remove 9:Place 0:Next *:Prev", 5, height - 5, Graphics.BOTTOM | Graphics.LEFT);
    }

}
