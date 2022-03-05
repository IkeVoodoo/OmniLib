package me.ikevoodoo.omnilib.commands;

import java.util.List;

public record RuntimeComplexCommand(String name, boolean autoComplete, List<RuntimeSubcommand> commands) {
}
