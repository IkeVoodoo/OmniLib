package me.ikevoodoo.omnilib.events.items;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ItemEventManager {
    private ItemEventManager() {

    }

    private static final HashMap<ItemStack, ItemEvents> itemsEvents = new HashMap<>();

    public static void registerItem(ItemStack item, ItemEventHandler... eventHandlers) {
        ItemEvents events = new ItemEvents(item);
        for (ItemEventHandler eventHandler : eventHandlers) {
            events.addHandler(eventHandler);
        }
        itemsEvents.put(item, events);
    }

    public static void unregisterItem(ItemStack item) {
        itemsEvents.remove(item);
    }

    public static ItemEvents get(ItemStack item) {
        return itemsEvents.get(item);
    }

}
