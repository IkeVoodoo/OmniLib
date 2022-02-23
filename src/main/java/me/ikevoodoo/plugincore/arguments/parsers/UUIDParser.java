package me.ikevoodoo.plugincore.arguments.parsers;

import me.ikevoodoo.plugincore.arguments.ArgumentParser;

import java.util.UUID;

public class UUIDParser implements ArgumentParser<UUID> {
    @Override
    public UUID parse(String argument) {
        return UUID.fromString(argument);
    }
}
