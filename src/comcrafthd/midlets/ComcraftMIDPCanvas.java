/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comcrafthd.midlets;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.GameCanvas;

/**
 * @author quead
 */
public final class ComcraftMIDPCanvas extends GameCanvas {
    
    public static ComcraftMIDPCanvas instance;
    
    /**
     * constructor
     */
    public ComcraftMIDPCanvas() {
        super(true);
        
        instance = this;
    } 
    
    /**
     * paint
     */
    public void paint(Graphics g) {
        g.drawString("Sample Text",0,0,Graphics.TOP|Graphics.LEFT);
    }
    
    public Graphics getGraphics() {
        return super.getGraphics();
    }
    
    public void flushGraphics() {
        super.flushGraphics();
    }

}