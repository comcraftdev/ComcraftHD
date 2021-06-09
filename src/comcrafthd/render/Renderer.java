/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd.render;

import comcrafthd.Chunk;
import comcrafthd.ChunkList;
import comcrafthd.ComcraftGame;
import comcrafthd.Log;
import comcrafthd.midlets.ComcraftMIDPCanvas;
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
public final class Renderer {

    public static final byte BLOCK_RENDER_SIZE = 8;

    public final ChunkRenderer chunkRenderer;

    private final ComcraftMIDPCanvas comcraftCanvas;
    private final Graphics graphics;
    private final Graphics3D g3d;

    public final World world = new World();

    public Renderer() {
        chunkRenderer = new ChunkRenderer();

        comcraftCanvas = ComcraftMIDPCanvas.instance;
        graphics = comcraftCanvas.getGraphics();
        g3d = Graphics3D.getInstance();

        initializeWorld();
    }

    public void initialize() {

    }

    public void renderTick() {
        renderChunksCache();

        int hints = Graphics3D.OVERWRITE;

        g3d.bindTarget(graphics, true, hints);
        g3d.clear(null);
        g3d.render(world);
        g3d.releaseTarget();

        comcraftCanvas.flushGraphics();
    }

    private void renderChunksCache() {
        ChunkList chunkList = ComcraftGame.instance.chunkList;

        for (int n = chunkList.chunksSize - 1; n >= 0; --n) {
            Chunk chunk = chunkList.chunks[n];

            if (chunk.renderCache.done == false) {
                renderChunkCache(chunk);
            }
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

    private void initializeWorld() {
        Log.info(this, "initializeWorld() entered");

        Background background = new Background();
        background.setColor(0x87ceeb);
        world.setBackground(background);

        Light myLight = new Light();
        myLight.setColor(0xffffff); // white light
        myLight.setIntensity(1.25f);

        world.addChild(myLight);

        Camera camera = new Camera();
        camera.setPerspective(90.0f, // field of view
                (float) comcraftCanvas.getWidth() / (float) comcraftCanvas.getHeight(), // aspectRatio
                0.1f, // near clipping plane
                1000.0f); // far clipping plan

        camera.setTranslation(0, 10, 20);

        world.addChild(camera);
        world.setActiveCamera(camera);

//        try {
//            Node testCube = TestCube.getTestCube();
//            world.addChild(testCube);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }

        Log.info(this, "initializeWorld() finished");
    }

}
