package me.ikevoodoo.plugincore.arguments.parsers;

import me.ikevoodoo.plugincore.arguments.ArgumentParser;
import org.bukkit.Material;

public class MaterialParser implements ArgumentParser<Material> {
    @Override
    public Material parse(String argument) {
        try {
            return Material.valueOf(argument.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
