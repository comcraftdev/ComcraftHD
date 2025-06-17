/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd.client.midlets;

import comcrafthd.ComcraftGameThread;
import comcrafthd.client.Keyboard;
import javax.microedition.lcdui.*;

/**
 * @author quead
 */
public final class GameCanvas extends javax.microedition.lcdui.game.GameCanvas {

    public static GameCanvas instance;

    private final Keyboard keyboard = new Keyboard();
    private final ComcraftGameThread gameThread;

    public GameCanvas(ComcraftGameThread gameThread) {
        super(false);
        
        this.gameThread = gameThread;
        instance = this;
    }

    public Graphics getGraphics() {
        return super.getGraphics();
    }

    public void flushGraphics() {
        super.flushGraphics();
    }

    protected void showNotify() {
        if (gameThread != null) {
            gameThread.resume();
        }
    }

    protected void hideNotify() {
        if (gameThread != null) {
            gameThread.pause();
        }
    }
    
    protected void keyRepeated(int keyCode) {
//        keyboard.notifyKeyRepeated(keyCode);
    }

    protected void keyReleased(int keyCode) {
        keyboard.notifyKeyReleased(keyCode > 0 ? keyCode : getGameAction(keyCode));
    }

    protected void keyPressed(int keyCode) {
        keyboard.notifyKeyPressed(keyCode > 0 ? keyCode : getGameAction(keyCode));
    }
    
}
