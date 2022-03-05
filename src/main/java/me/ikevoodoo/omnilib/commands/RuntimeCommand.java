package me.ikevoodoo.omnilib.commands;

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

    }

}
