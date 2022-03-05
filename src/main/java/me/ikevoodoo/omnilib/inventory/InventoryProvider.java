package me.ikevoodoo.omnilib.inventory;

import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class InventoryProvider {

    private InventoryProvider() {}

    private static final HashMap<Plugin, PluginInventoryProvider> providers = new HashMap<>();

    public static OmniInventory create(Plugin plugin, String id) {
        if(!providers.containsKey(plugin)) {
            PluginInventoryProvider provider = new PluginInventoryProvider();
            providers.put(plugin, provider);
            return provider.register(id);
        }
        return providers.get(plugin).register(id);
    }

    public static OmniInventory get(Plugin plugin, String id) {
        if (!providers.containsKey(plugin)) {
            return create(plugin, id);
        }

        if(!providers.get(plugin).contains(id)) {
            return create(plugin, id);
        }

        return providers.get(plugin).get(id);
    }

    public static void remove(Plugin plugin, String id) {
        if(providers.containsKey(plugin)) {
            providers.get(plugin).remove(id);
            return;
        }

        throw new IllegalArgumentException("Plugin has no registered inventories");
    }
}
