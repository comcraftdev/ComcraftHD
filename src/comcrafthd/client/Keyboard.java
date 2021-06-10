/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd.client;

/**
 *
 * @author quead
 */
public final class Keyboard {
    
    public static final int MAX_KEY = 128;
    public static final long HOLD_THRESHOLD = 500;
    
    public static Keyboard instance;
    
    private final boolean[] pressed = new boolean[MAX_KEY];
    private final long[] pressedTime = new long[MAX_KEY];
    private final boolean[] quickReleased = new boolean[MAX_KEY];
    
    public Keyboard() {
        if (instance != null) {
            throw new IllegalStateException("Keyboard");
        }
        
        instance = this;
    }
    
    public synchronized void notifyKeyRepeated(int keyCode) {
    }

    public synchronized void notifyKeyReleased(int keyCode) {
        if (pressed[keyCode] && (System.currentTimeMillis() - pressedTime[keyCode] < HOLD_THRESHOLD)) {
            quickReleased[keyCode] = true;
        }
        
        pressed[keyCode] = false;
    }

    public synchronized void notifyKeyPressed(int keyCode) {
        pressed[keyCode] = true;
        pressedTime[keyCode] = System.currentTimeMillis();
    }
    
    public synchronized boolean isPressed(int keyCode) {
        return pressed[keyCode];
    }
    
    public synchronized boolean isHeld(int keyCode) {
        return pressed[keyCode] && (System.currentTimeMillis() - pressedTime[keyCode] > HOLD_THRESHOLD);
    }
    
    public synchronized boolean isQuickReleased(int keyCode) {
        return quickReleased[keyCode];
    }
    
    public synchronized void clearQuickReleased(int keyCode) {
        quickReleased[keyCode] = false;
    }
    
    public synchronized void clearHeld(int keyCode) {
        pressedTime[keyCode] = System.currentTimeMillis();
    }
    
}
