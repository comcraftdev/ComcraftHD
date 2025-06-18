package comcrafthd;

import comcrafthd.client.*;

public final class ComcraftGame {

    public static ComcraftGame instance;

    public final ComcraftGameConfiguration gameConfiguration;
    public final ComcraftRenderer renderer;

    public final ChunkPartitionPool chunkPartitionPool;
    public final ChunkGenerator chunkGenerator;
    public final ChunkList chunkList;
    public final ChunkWorld chunkWorld;
    public final KeyboardMapping keyboardMapping;
    public final CameraMovement cameraMovement;

    public ComcraftGame(ComcraftGameConfiguration gameConfiguration, ComcraftRenderer renderer) {
        if (instance != null) {
            throw new IllegalStateException("ComcraftGame instance already exists");
        }

        instance = this;

        this.gameConfiguration = gameConfiguration;
        this.renderer = renderer;

        chunkPartitionPool = new ChunkPartitionPool();
        chunkGenerator = new ChunkGenerator();
        chunkList = new ChunkList();
        chunkWorld = new ChunkWorld();
        keyboardMapping = new KeyboardMapping();
        cameraMovement = new CameraMovement();
    }

    public void initialize() {
        Log.info(this, "initialize() entered");

        BlockMaterialDefinitions.initialize();
        BlockRendererDefinitions.initialize();

        renderer.start();

        System.gc();

        Log.info(this, "initialize() finished");
    }

    public void stop() {
        renderer.stop();
    }

    public void tick() {
        cameraMovement.tick();

        renderer.render();
    }

    public void clear() {
        instance = null;
    }

    public void tidyUpMemory() {
        System.gc();
    }

}
