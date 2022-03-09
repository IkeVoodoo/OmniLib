package me.ikevoodoo.omnilib.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.lang.reflect.Method;
import java.util.List;

public class CommandRegistry {

    private static CommandRegistry instance;

    private CommandMap commandMap;

    private CommandRegistry() {
        try {
            Method cmdMap = Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap", null);
            cmdMap.setAccessible(true);
            commandMap = (CommandMap) cmdMap.invoke(Bukkit.getServer(), null);
        } catch (Exception e) {
            e.printStackTrace();
            commandMap = null;
        }
    }

    public static CommandRegistry getInstance() {
        if (instance == null) {
            instance = new CommandRegistry();
        }
        return instance;
    }

    public void add(Object obj) {
        CommandParser parser = CommandParser.getInstance();
        if(parser.isComplex(obj)) {
            RuntimeComplexCommand cmd = parser.parseComplex(obj);
            System.out.println(cmd);
        } else {
            List<RuntimeCommand> commands = parser.parseSimple(obj);
            for (RuntimeCommand cmd : commands) {
                register(cmd);
            }
        }
    }

    public static void main(String[] args) {
        CommandRegistry registry = CommandRegistry.getInstance();
        registry.add(new Testing());
        registry.add(new Testing2());
    }

    private void register(RuntimeCommand cmd) {
        BukkitCommand command = new BukkitCommand(cmd.name()) {
            @Override
            public boolean execute(CommandSender sender, String label, String[] args) {
                cmd.execute(sender, label, args);
                return true;
            }
        };

        commandMap.register(cmd.prefix(), command);
    }

}
