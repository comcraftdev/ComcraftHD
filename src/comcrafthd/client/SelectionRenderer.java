package comcrafthd.client;

import comcrafthd.*;
import javax.microedition.m3g.*;

public class SelectionRenderer {
    
    // Selection box extends 1 unit beyond block bounds
    private static final byte S = 9;  // Block size + 1
    private static final byte O = -1; // Offset from origin
    
    // Vertex positions for 8 corners of selection cube
    private static final byte[] VERT = {
        // Front face (z+)
        O, O, S,  // 0: bottom-left-front
        S, O, S,  // 1: bottom-right-front
        S, S, S,  // 2: top-right-front
        O, S, S,  // 3: top-left-front
        // Back face (z-)
        O, O, O,  // 4: bottom-left-back
        S, O, O,  // 5: bottom-right-back
        S, S, O,  // 6: top-right-back
        O, S, O   // 7: top-left-back
    };
    
    // Face definitions as triangle strips
    private static final int[] STRIP_INDICES = {
        // Front face
        0, 1, 3, 2,
        // Back face  
        5, 4, 6, 7,
        // Top face
        3, 2, 7, 6,
        // Bottom face
        4, 5, 0, 1,
        // Right face
        1, 5, 2, 6,
        // Left face
        4, 0, 7, 3
    };
    
    private static final int[] STRIP_LENGTHS = {4, 4, 4, 4, 4, 4};
    
    private static final int VERTEX_COUNT = 8;
    private static final float SCALE = 1.0f / 8.0f;
    
    /* */
    
    private final Mesh selectionMesh;
    private final Transform transform;
    
    public SelectionRenderer() {
        this.transform = new Transform();
        this.selectionMesh = createSelectionMesh();
    }
    
    private Mesh createSelectionMesh() {
        // Create vertex array
        VertexArray vertArray = new VertexArray(VERTEX_COUNT, 3, 1);
        vertArray.set(0, VERTEX_COUNT, VERT);
        
        // Create vertex buffer
        VertexBuffer vertexBuffer = new VertexBuffer();
        vertexBuffer.setPositions(vertArray, SCALE, null);
        
        // Create index buffer
        IndexBuffer indexBuffer = new TriangleStripArray(STRIP_INDICES, STRIP_LENGTHS);
        
        // Create appearance
        Appearance appearance = createSelectionAppearance();
        
        return new Mesh(vertexBuffer, indexBuffer, appearance);
    }
    
    private Appearance createSelectionAppearance() {
        Appearance appearance = new Appearance();
        
        // Set polygon mode
        PolygonMode polygonMode = new PolygonMode();
        polygonMode.setCulling(PolygonMode.CULL_NONE);
        appearance.setPolygonMode(polygonMode);
        
        // Set material with semi-transparent white
        Material material = new Material();
        material.setColor(Material.DIFFUSE, 0x80FFFFFF);
        material.setColor(Material.AMBIENT, 0x40FFFFFF);
        material.setColor(Material.EMISSIVE, 0x20FFFFFF);
        appearance.setMaterial(material);
        
        // Enable transparency
        CompositingMode compositingMode = new CompositingMode();
        compositingMode.setBlending(CompositingMode.ALPHA);
        compositingMode.setAlphaThreshold(0.0f);
        compositingMode.setDepthWriteEnable(false);
        appearance.setCompositingMode(compositingMode);
        
        return appearance;
    }
    
    public void render(Graphics3D g3d, BlockPicker picker) {
        if (!picker.hasTarget) {
            return;
        }
        
        // Position at target block
        transform.setIdentity();
        transform.postTranslate(
            picker.targetX,
            picker.targetY,
            picker.targetZ
        );
        
        // Render selection mesh
        g3d.render(selectionMesh, transform);
    }
}