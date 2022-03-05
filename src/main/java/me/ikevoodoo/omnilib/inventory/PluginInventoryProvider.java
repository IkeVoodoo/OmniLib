package me.ikevoodoo.omnilib.inventory;

import java.util.HashMap;

public class PluginInventoryProvider {

    protected PluginInventoryProvider() {}

    private final HashMap<String, OmniInventory> inventories = new HashMap<>();

    public OmniInventory register(String id) {
        if (contains(id)) {
            throw new IllegalArgumentException("Inventory with id " + id + " already exists");
        }

        OmniInventory inventory = new OmniInventory();
        inventories.put(id, inventory);
        return inventory;
    }

    public boolean contains(String id) {
        return inventories.containsKey(id);
    }

    public OmniInventory get(String id) {
        return inventories.get(id);
    }

    public void remove(String id) {
        inventories.remove(id);
    }

}
