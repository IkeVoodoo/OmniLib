package me.ikevoodoo.omnilib.events.items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemEvent {

    private final Player player;
    private final ItemStack item;

    private boolean cancelled = false;

    public ItemEvent(Player player, ItemStack item) {
        this.player = player;
        this.item = item;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return cancelled;
    }

}
