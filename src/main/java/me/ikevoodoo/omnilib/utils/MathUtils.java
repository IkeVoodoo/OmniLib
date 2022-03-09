package me.ikevoodoo.omnilib.utils;

public class MathUtils {

    private MathUtils() {}

    public static boolean isBetween(double value, double min, double max) {
        double mn = Math.min(min, max);
        double mx = Math.max(min, max);
        return value >= mn && value <= mx;
    }

}
