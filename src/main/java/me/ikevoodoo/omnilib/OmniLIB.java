package me.ikevoodoo.omnilib;

import me.ikevoodoo.omnilib.events.items.listeners.ItemEventListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class OmniLIB extends JavaPlugin {

    private static OmniLIB instance;

    @Override
    public void onEnable() {
        instance = this;
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new ItemEventListener(), this);
    }

    public static OmniLIB getInstance() {
        return instance;
    }

}
