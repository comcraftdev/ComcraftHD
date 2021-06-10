/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd.client;

import java.io.IOException;
import javax.microedition.lcdui.Image;
import javax.microedition.m3g.Appearance;
import javax.microedition.m3g.Image2D;
import javax.microedition.m3g.IndexBuffer;
import javax.microedition.m3g.Material;
import javax.microedition.m3g.Mesh;
import javax.microedition.m3g.Node;
import javax.microedition.m3g.Texture2D;
import javax.microedition.m3g.TriangleStripArray;
import javax.microedition.m3g.VertexArray;
import javax.microedition.m3g.VertexBuffer;

/**
 *
 * @author quead
 */
public final class TestCube {

    public static Node getTestCube() throws IOException {
        short[] vert = {
            10, 10, 10, -10, 10, 10, 10, -10, 10, -10, -10, 10, // front
            -10, 10, -10, 10, 10, -10, -10, -10, -10, 10, -10, -10, // back
            -10, 10, 10, -10, 10, -10, -10, -10, 10, -10, -10, -10, // left
            10, 10, -10, 10, 10, 10, 10, -10, -10, 10, -10, 10, // right
            10, 10, -10, -10, 10, -10, 10, 10, 10, -10, 10, 10, // top
            10, -10, 10, -10, -10, 10, 10, -10, -10, -10, -10, -10};

        VertexArray vertArray = new VertexArray(vert.length / 3, 3, 2);
        vertArray.set(0, vert.length / 3, vert);

        byte[] norm = {
            0, 0, 127, 0, 0, 127, 0, 0, 127, 0, 0, 127,
            0, 0, -127, 0, 0, -127, 0, 0, -127, 0, 0, -127,
            -127, 0, 0, -127, 0, 0, -127, 0, 0, -127, 0, 0,
            127, 0, 0, 127, 0, 0, 127, 0, 0, 127, 0, 0,
            0, 127, 0, 0, 127, 0, 0, 127, 0, 0, 127, 0,
            0, -127, 0, 0, -127, 0, 0, -127, 0, 0, -127, 0};

        VertexArray normArray = new VertexArray(norm.length / 3, 3, 1);
        normArray.set(0, norm.length / 3, norm);
        
        // per vertex texture coordinates
        short[] tex = {
            1, 0, 0, 0, 1, 1, 0, 1,
            1, 0, 0, 0, 1, 1, 0, 1,
            1, 0, 0, 0, 1, 1, 0, 1,
            1, 0, 0, 0, 1, 1, 0, 1,
            1, 0, 0, 0, 1, 1, 0, 1,
            1, 0, 0, 0, 1, 1, 0, 1};
        
        // create a vertex array for the texture coordinates of the object
        VertexArray texArray = new VertexArray(tex.length / 2, 2, 2);
        texArray.set(0, tex.length / 2, tex);
        // the length of each triangle strip 
        int[] stripLen = {4, 4, 4, 4, 4, 4};

        // create the VertexBuffer for our object
        VertexBuffer vb = new VertexBuffer();
        vb.setPositions(vertArray, 0.1f, null);
        vb.setNormals(normArray);
        vb.setTexCoords(0, texArray, 1.0f, null); // unit scale, zero bias

        // create the index buffer for our object (this tells how to
        // create triangle strips from the contents of the vertex buffer).
        IndexBuffer iIb = new TriangleStripArray(0, stripLen);

        // load the image for the texture
        Image iImage = Image.createImage("/test.png");
        // create the Image2D (we need this so we can make a Texture2D)
        Image2D image2D = new Image2D(Image2D.RGB, iImage);

        // create the Texture2D and enable mipmapping
        // texture color is to be modulated with the lit material color
        Texture2D texture = new Texture2D(image2D);
        texture.setFiltering(Texture2D.FILTER_NEAREST,
                Texture2D.FILTER_NEAREST);
        texture.setWrapping(Texture2D.WRAP_CLAMP,
                Texture2D.WRAP_CLAMP);
        texture.setBlending(Texture2D.FUNC_MODULATE);

        Material iMaterial = new Material();
        iMaterial.setColor(Material.DIFFUSE, 0xFFFFFFFF); // white
        iMaterial.setColor(Material.SPECULAR, 0xFFFFFFFF); // white
        iMaterial.setShininess(100.0f);

        // create the appearance
        Appearance iAppearance = new Appearance();
        iAppearance.setTexture(0, texture);
        iAppearance.setMaterial(iMaterial);

        return new Mesh(vb, iIb, iAppearance);
    }

}
