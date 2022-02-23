package me.ikevoodoo.plugincore;

import org.bukkit.plugin.java.JavaPlugin;

public final class PluginCore extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        CommandRegistry.getInstance().registerClass(new Test());
        CommandRegistry.getInstance().registerClass(new Test2());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
