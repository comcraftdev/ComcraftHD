/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd.client.midlets;

import comcrafthd.client.Keyboard;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.GameCanvas;

/**
 * @author quead
 */
public final class ComcraftMIDPCanvas extends GameCanvas {

    public static ComcraftMIDPCanvas instance;

    private final Keyboard keyboard = new Keyboard();

    public ComcraftMIDPCanvas() {
        super(false);

        instance = this;
    }

    public Graphics getGraphics() {
        return super.getGraphics();
    }

    public void flushGraphics() {
        super.flushGraphics();
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
