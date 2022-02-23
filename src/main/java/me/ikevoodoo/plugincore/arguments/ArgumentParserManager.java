package me.ikevoodoo.plugincore.arguments;

import me.ikevoodoo.plugincore.arguments.parsers.MaterialParser;
import me.ikevoodoo.plugincore.arguments.parsers.PlayerParser;
import me.ikevoodoo.plugincore.arguments.parsers.UUIDParser;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ArgumentParserManager {

    private static final HashMap<Class<?>, ArgumentParser<?>> parsers = new HashMap<>();

    static {
        register(UUID.class, new UUIDParser());
        register(Player.class, new PlayerParser());
        register(Material.class, new MaterialParser());
    }

    public static <T> ArgumentParser<T> get(Class<T> clazz) {
        ArgumentParser<?> parser = parsers.get(clazz);
        return parser == null ? null : (ArgumentParser<T>) parser;
    }

    public static <T> void register(Class<T> clazz, ArgumentParser<T> parser) {
        parsers.put(clazz, parser);
    }



}
