/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd.render;

import javax.microedition.m3g.Appearance;

/**
 *
 * @author quead
 */
public class BlockMaterial {
    
    public final byte id;
    
    public final Appearance appearance;
    
    public BlockMaterial(byte id, Appearance appearance) {
        this.id = id;
        this.appearance = appearance;
    }
    
}
