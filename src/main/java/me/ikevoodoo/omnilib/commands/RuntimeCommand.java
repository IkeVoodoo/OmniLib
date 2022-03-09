package me.ikevoodoo.omnilib.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;
import java.util.List;

public record RuntimeCommand(
        String name,
        String prefix,
        List<RuntimeArgument> args,
        boolean autoComplete,
        Method method,
        Object instance) {

    public void execute(CommandSender sender, String label, String[] args) {
        try {
            if(args.length < this.args.size()) {
                StringBuilder sb = new StringBuilder();
                sb.append(ChatColor.DARK_RED).append("/").append(label);
                for (String arg : args) {
                    sb.append(" ").append(arg);
                }
                int diff = this.args.size() - args.length;
                sb.append("<-- [HERE] Missing argumen").append(diff == 1 ? "t" : "ts").append(": ");
                for (int i = args.length; i < this.args.size(); i++) {
                    sb.append(this.args.get(i).name()).append(" ");
                }
                sender.sendMessage(sb.toString());
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
