package me.ikevoodoo.omnilib.utils;

import java.util.Random;

public class StringUtils {
    private StringUtils() {

    }

    private static final Random random = new Random();
    private static final char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    public static String random(int length) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < length; i++) {
            s.append(chars[random.nextInt(chars.length)]);
        }
        return s.toString();
    }
}
