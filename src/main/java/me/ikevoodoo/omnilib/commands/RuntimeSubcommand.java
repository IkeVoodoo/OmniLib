package me.ikevoodoo.omnilib.commands;

import java.util.List;

public record RuntimeSubcommand(String name, String prefix, List<RuntimeArgument> args, boolean autoComplete,
                                String prev) {
}
