package me.ikevoodoo.plugincore;

import me.ikevoodoo.plugincore.annotations.commands.Command;
import me.ikevoodoo.plugincore.annotations.commands.CommandUser;
import me.ikevoodoo.plugincore.arguments.Arguments;
import me.ikevoodoo.plugincore.utils.DownloadUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Test {

    @Command(name = "broadcast", prefix = "test", usableBy = CommandUser.PLAYER)
    public void test(CommandSender sender, String[] args) {
        Bukkit.broadcastMessage("[BROADCAST] " + sender.getName() + ": " + String.join(" ", args));
    }

    @Command(name = "kick", prefix = "test")
    public void kick(CommandSender sender, Arguments args) {
        Player player = args.getFirst(Player.class);
        if(player == null) {
            sender.sendMessage("[KICK] Player not found");
            return;
        }
        player.kickPlayer("[KICK] " + sender.getName() + ": " + args.text(1));
    }

    @Command(name = "materialinfo", prefix = "test", aliases = {"mi"})
    public void materialInfo(CommandSender sender, Arguments args) {
        Material mat = args.getFirst(Material.class);
        if(mat == null) {
            sender.sendMessage("[MATERIALINFO] Material not found");
            return;
        }
        sender.sendMessage("[MATERIALINFO] Key: " + mat.getKey()
                + ", Mame: " + mat.name()
                + ", Max durability: " + mat.getMaxDurability()
                + ", Max stack: " + mat.getMaxStackSize()
                + ", Is burnable: " + mat.isBurnable()
        );
    }

    @Command(name = "download", prefix = "test", aliases = {"dl"})
    public void download(CommandSender sender, String[] args) {
        sender.sendMessage("[DOWNLOADING] Starting: " + String.join(" ", args));

        for (String arg : args) {
            try {
                sender.sendMessage("[DOWNLOADING] In progress: " + arg);
                DownloadUtils.download(arg);
            } catch (Exception e) {
                sender.sendMessage("[DOWNLOADING] " + arg + " FAILED");
                e.printStackTrace();
            }
        }

        sender.sendMessage("[DOWNLOADING] COMPLETE");
    }

}
