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
public final class TestHelper {
    
    public static void validate(boolean expression) {
        validate(expression, "Assertion failed");
    }

    public static void validate(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }
    
}
