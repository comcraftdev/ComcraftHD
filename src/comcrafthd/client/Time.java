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
public final class Time {
    
    public static float dt;
    
    public static long currentTime;
    
    private static long lastTime;
    
    public static void reset() {
        dt = 0;
        lastTime = 0;
        currentTime = 0;
    }
    
    public static void tick() {
        long currTime = System.currentTimeMillis();
        
        if (lastTime == 0) {
            lastTime = currTime;
            return;
        }
        
        long diff = currTime - lastTime;
        dt = diff / 1000f;
        lastTime = currTime;
        currentTime = currTime;
    }
    
}
