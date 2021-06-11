/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd.client;

import comcrafthd.Chunk;
import comcrafthd.ChunkList;
import comcrafthd.ComcraftGame;
import comcrafthd.Log;
import comcrafthd.client.midlets.ComcraftMIDPCanvas;
import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.m3g.Background;
import javax.microedition.m3g.Camera;
import javax.microedition.m3g.Graphics3D;
import javax.microedition.m3g.Light;
import javax.microedition.m3g.Node;
import javax.microedition.m3g.World;

/**
 *
 * @author quead
 */
public final class ComcraftRenderer {

    public static final byte BLOCK_RENDER_SIZE = 8;
    
    public static final int TEXTURE_ATLAS_SIZE = 16;

    public final ChunkRenderer chunkRenderer;

    private final ComcraftMIDPCanvas comcraftCanvas;
    private final Graphics graphics;
    private final Graphics3D g3d;

    public final World world = new World();
    
    public Camera camera;

    public ComcraftRenderer() {
        chunkRenderer = new ChunkRenderer();

        comcraftCanvas = ComcraftMIDPCanvas.instance;
        graphics = comcraftCanvas.getGraphics();
        g3d = Graphics3D.getInstance();

        initializeWorld();
    }

    public void initialize() {

    }

    public void render() {
        ComcraftGame.instance.chunkList.triggerRenderChunks(this);

        int hints = Graphics3D.OVERWRITE;

        g3d.bindTarget(graphics, true, hints);
        g3d.clear(null);
        g3d.render(world);
        g3d.releaseTarget();
        
        comcraftCanvas.flushGraphics();
    }

    public void renderChunkCallback(Chunk chunk) {
        if (chunk.renderCache.done == false) {
                renderChunkCache(chunk);
            }
    }
    
    private void renderChunkCache(Chunk chunk) {
        if (chunk.renderCache.node != null) {
            world.removeChild(chunk.renderCache.node);
            chunk.renderCache.node = null;
        }

        Node node = chunkRenderer.renderChunk(chunk);
        if (node != null) {
            chunk.renderCache.node = node;
            world.addChild(node);
        }
        
        chunk.renderCache.done = true;
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
                (float) comcraftCanvas.getWidth() / (float) comcraftCanvas.getHeight(), // aspectRatio
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
