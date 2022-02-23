package me.ikevoodoo.plugincore.utils;

public class AssertUtils {

    public static void assertInBounds(int value, int length, String message) {
        if (value < 0 || value > length) {
            throw new AssertionError(message);
        }
    }

}
