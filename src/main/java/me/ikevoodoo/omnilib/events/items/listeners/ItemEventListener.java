package me.ikevoodoo.omnilib.events.items.listeners;

import me.ikevoodoo.omnilib.events.items.ItemEvent;
import me.ikevoodoo.omnilib.events.items.ItemEventManager;
import me.ikevoodoo.omnilib.events.items.ItemEventType;
import me.ikevoodoo.omnilib.events.items.ItemEvents;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemEventListener implements Listener {

    @EventHandler
    public void on(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Player player = event.getPlayer();

        ItemEvents events = ItemEventManager.get(item);
        ItemEvent e = new ItemEvent(player, item);
        ItemEventType type = switch (event.getAction()) {
            case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> ItemEventType.LEFT_CLICK;
            case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> ItemEventType.RIGHT_CLICK;
            default -> ItemEventType.UNKNOWN;
        };
        events.invoke(type, e);
        event.setCancelled(e.isCancelled());
    }

    @EventHandler
    public void on(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();
        Player player = event.getPlayer();

        ItemEvents events = ItemEventManager.get(item);
        ItemEvent e = new ItemEvent(player, item);
        events.invoke(ItemEventType.DROP, e);
        event.setCancelled(e.isCancelled());
    }

}
