package me.ikevoodoo.omnilib.events.items;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemEvents {

    private final ItemStack item;
    private final List<ItemEventHandler> handlers = new ArrayList<>();

    public ItemEvents(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

    public void addHandler(ItemEventHandler handler) {
        handlers.add(handler);
    }

    public void invoke(ItemEventType type, ItemEvent event) {
        switch (type) {
            case RIGHT_CLICK -> handlers.forEach(handler -> handler.handleRightClick(event));
            case LEFT_CLICK -> handlers.forEach(handler -> handler.handleLeftClick(event));
            case DROP -> handlers.forEach(handler -> handler.handleDrop(event));
        }
    }

}
