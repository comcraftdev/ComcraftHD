package comcrafthd.entities;

import comcrafthd.*;
import javax.microedition.m3g.*;

/**
 * Entity representing a falling block (sand, gravel, etc).
 */
public class FallingBlockEntity extends Entity {
    
    private static final float GRAVITY = -9.8f;
    private static final float TERMINAL_VELOCITY = -50.0f;
    
    private final int blockId;
    private final int metadata;
    
    public FallingBlockEntity(ComcraftGame game, float x, float y, float z, int blockId, int metadata) {
        super(game, x, y, z);
        this.blockId = blockId;
        this.metadata = metadata;
        this.vy = 0;
        
        // Create the visual representation
        createMesh();
    }
    
    private void createMesh() {
        Block block = BlockDefinitions.blocks[blockId];
        if (block == null) {
            return;
        }
        
        // Create a simple cube mesh for the falling block
        VertexBuffer vertexBuffer = new VertexBuffer();
        vertexBuffer.setDefaultColor(0xFFFFFFFF);
        
        // Use the block's texture
        // This is simplified - in a full implementation we'd use the block's renderer
        int texX = block.texX[Block.SIDE_FRONT];
        int texY = block.texY[Block.SIDE_FRONT];
        
        // Create vertex and texture coordinate arrays
        short[] vertices = new short[] {
            // Front face
            -4, -4, 4,   4, -4, 4,   4, 4, 4,   -4, 4, 4,
            // Back face  
            -4, -4, -4,  -4, 4, -4,  4, 4, -4,  4, -4, -4,
            // Top face
            -4, 4, -4,   -4, 4, 4,   4, 4, 4,   4, 4, -4,
            // Bottom face
            -4, -4, -4,  4, -4, -4,  4, -4, 4,  -4, -4, 4,
            // Right face
            4, -4, -4,   4, 4, -4,   4, 4, 4,   4, -4, 4,
            // Left face
            -4, -4, -4,  -4, -4, 4,  -4, 4, 4,  -4, 4, -4
        };
        
        // Scale to block size (1/8 unit = 1 block)
        VertexArray vertexArray = new VertexArray(vertices.length / 3, 3, 2);
        vertexArray.set(0, vertices.length / 3, vertices);
        
        // Set up texture coordinates (simplified - all faces same texture)
        float u0 = texX / 16.0f;
        float v0 = texY / 16.0f;
        float u1 = (texX + 1) / 16.0f;
        float v1 = (texY + 1) / 16.0f;
        
        short[] texCoords = new short[48]; // 24 vertices * 2 coords
        for (int i = 0; i < 6; i++) { // 6 faces
            int base = i * 8;
            // Bottom-left
            texCoords[base + 0] = (short)(u0 * 255);
            texCoords[base + 1] = (short)(v1 * 255);
            // Bottom-right
            texCoords[base + 2] = (short)(u1 * 255);
            texCoords[base + 3] = (short)(v1 * 255);
            // Top-right
            texCoords[base + 4] = (short)(u1 * 255);
            texCoords[base + 5] = (short)(v0 * 255);
            // Top-left
            texCoords[base + 6] = (short)(u0 * 255);
            texCoords[base + 7] = (short)(v0 * 255);
        }
        
        VertexArray texCoordArray = new VertexArray(texCoords.length / 2, 2, 2);
        texCoordArray.set(0, texCoords.length / 2, texCoords);
        
        vertexBuffer.setPositions(vertexArray, 0.125f, null);
        vertexBuffer.setTexCoords(0, texCoordArray, 1.0f / 255.0f, null);
        
        // Create index buffer for triangles
        int[] indices = new int[36]; // 6 faces * 2 triangles * 3 vertices
        for (int i = 0; i < 6; i++) {
            int base = i * 4;
            int idx = i * 6;
            // Triangle 1
            indices[idx + 0] = base + 0;
            indices[idx + 1] = base + 1;
            indices[idx + 2] = base + 2;
            // Triangle 2
            indices[idx + 3] = base + 0;
            indices[idx + 4] = base + 2;
            indices[idx + 5] = base + 3;
        }
        
        IndexBuffer indexBuffer = new TriangleStripArray(indices, new int[] {indices.length});
        
        // Create appearance with texture
        Appearance appearance = new Appearance();
        
        // Load terrain texture (this should be cached somewhere)
        try {
            Object[] objects = Loader.load("/terrain.png");
            if (objects != null && objects.length > 0 && objects[0] instanceof Image2D) {
                Image2D terrainImage = (Image2D) objects[0];
                Texture2D texture = new Texture2D(terrainImage);
                texture.setFiltering(Texture2D.FILTER_NEAREST, Texture2D.FILTER_NEAREST);
                texture.setWrapping(Texture2D.WRAP_REPEAT, Texture2D.WRAP_REPEAT);
                appearance.setTexture(0, texture);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create mesh
        Mesh mesh = new Mesh(vertexBuffer, indexBuffer, appearance);
        
        // Create transform group for positioning
        Transform transform = new Transform();
        transform.postTranslate(x, y, z);
        
        node = new Group();
        ((Group)node).addChild(mesh);
        node.setTransform(transform);
    }
    
    public void update(float dt) {
        // Apply gravity
        vy += GRAVITY * dt;
        if (vy < TERMINAL_VELOCITY) {
            vy = TERMINAL_VELOCITY;
        }
        
        // Try to move
        float newY = y + vy * dt;
        
        // Check collision with ground
        if (isColliding(x, newY, z)) {
            // Place block at landing position
            int blockX = (int) Math.floor(x);
            int blockY = (int) Math.floor(y + 0.5f);
            int blockZ = (int) Math.floor(z);
            
            // Make sure the position is valid and empty
            ChunkWorld world = game.getWorld();
            if (Block.getId(world.get(blockX, blockY, blockZ)) == 0) {
                short blockData = (short) (blockId | (metadata << Block.BLOCK_META_SHIFT));
                world.set(blockX, blockY, blockZ, blockData);
            }
            
            // Remove this entity
            remove();
        } else {
            // Update position
            y = newY;
            
            // Update visual position
            if (node != null) {
                Transform transform = new Transform();
                transform.postTranslate(x, y, z);
                node.setTransform(transform);
            }
        }
        
        // Remove if fallen out of world
        if (y < -64) {
            remove();
        }
    }
}