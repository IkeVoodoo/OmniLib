package me.ikevoodoo.plugincore;

import me.ikevoodoo.plugincore.annotations.commands.BaseCommand;
import me.ikevoodoo.plugincore.annotations.commands.SubCommand;
import me.ikevoodoo.plugincore.arguments.Arguments;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@BaseCommand(name = "base", autoCompletion = true)
public class Test2 {
    @SubCommand(path = "/")
    public void defaultPath(CommandSender sender) {
        sender.sendMessage("BASE");
    }

    @SubCommand(path = "/nested")
    public void nestedPath(CommandSender sender) {
        sender.sendMessage("NESTED");
    }

    @SubCommand(path = "/nested/say")
    public void sayPath(CommandSender sender, Arguments args) {
        sender.sendMessage(sender.getName() + " says " + args.text());
    }

    @SubCommand(path = "/nested/say/all")
    public void allPath(CommandSender sender, Arguments args) {
        Bukkit.broadcastMessage(sender.getName() + " says to all " + args.text());
    }

    @SubCommand(path = "/say/all", redirectPath = "/nested/say/all")
    private void redirect() {

    }
}