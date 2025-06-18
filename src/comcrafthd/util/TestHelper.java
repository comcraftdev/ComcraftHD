package comcrafthd.util;

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
