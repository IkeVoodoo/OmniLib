package me.ikevoodoo.omnilib.commands;

public record RuntimeArgument(int argIndex, String name, Class<?> type, boolean optional) {
}
