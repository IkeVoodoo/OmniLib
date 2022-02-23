package me.ikevoodoo.plugincore.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerUtils {

    public static Player getOnline(String player) {
        Player p = Bukkit.getPlayer(player);
        if (p == null) {
            try {
                p = Bukkit.getPlayer(UUID.fromString(player));
            } catch (Exception e) {
                return null;
            }
        }

        return p;
    }

    public static OfflinePlayer getOffline(String player) {
        OfflinePlayer p = Bukkit.getOfflinePlayer(player);
        if (!p.hasPlayedBefore()) {
            try {
                p = Bukkit.getOfflinePlayer(UUID.fromString(player));
            } catch (Exception e) {
                return null;
            }
        }

        return p;
    }

}
