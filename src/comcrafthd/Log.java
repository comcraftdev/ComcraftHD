/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd;

/**
 *
 * @author quead
 */
public final class Log {
    
    public static final int LEVEL_DEBUG = 0;
    public static final int LEVEL_INFO = 1;
    
    public static final int CURRENT_LOG_LEVEL = LEVEL_DEBUG;
    
    public static void info(Object object, String msg) {
        if (CURRENT_LOG_LEVEL > LEVEL_INFO) {
            return;
        }
        
        System.out.println("INFO: [" + object.getClass().getName() + "] " + msg);
    }
    
    public static void debug(Object object, String msg) {
        if (CURRENT_LOG_LEVEL > LEVEL_DEBUG) {
            return;
        }
        
        System.out.println("DEBUG: [" + object.getClass().getName() + "] " + msg);
    }
    
}
