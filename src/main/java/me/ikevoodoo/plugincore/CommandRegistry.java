package me.ikevoodoo.plugincore;

import me.ikevoodoo.plugincore.annotations.commands.BaseCommand;
import me.ikevoodoo.plugincore.annotations.commands.Command;
import me.ikevoodoo.plugincore.annotations.commands.CommandUser;
import me.ikevoodoo.plugincore.annotations.commands.SubCommand;
import me.ikevoodoo.plugincore.arguments.Arguments;
import me.ikevoodoo.plugincore.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CommandRegistry {

    private static CommandRegistry instance;

    private final List<Object> objects = new ArrayList<>();

    private final List<String> commands = new ArrayList<>();

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

    public void registerClass(Object obj) {
        objects.add(obj);
        process();
    }

    private enum ParameterType {
        SENDER,
        LABEL,
        STRING_ARGS,
        ARGS
    }

    private record CommandData(Method method, ParameterType[] types, Object obj) {

        public boolean execute(CommandSender sender, String label, String[] args) {
            try {
                Object[] params = new Object[types.length];
                for (int i = 0; i < types.length; i++) {
                    switch (types[i]) {
                        case SENDER -> params[i] = sender;
                        case LABEL -> params[i] = label;
                        case STRING_ARGS -> params[i] = args;
                        case ARGS -> params[i] = new Arguments(args);
                    }
                }
                Object result = method.invoke(obj, params);

                if (result == null) {
                    return true;
                }

                if (result instanceof String text) {
                    sender.sendMessage(ChatUtils.color(text));
                }

                if (result instanceof List list) {
                    if(list.size() > 0) {
                        Object first = list.get(0);
                        if(first instanceof String) {
                            for (Object o : list) {
                                sender.sendMessage(ChatUtils.color((String) o));
                            }
                        }
                    }
                }

                if (result instanceof Boolean bool) {
                    return bool;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    private record SubCommandData(SubCommand command, Method method, ParameterType[] types, Object obj) {
        public boolean execute(CommandSender sender, String label, String[] args) {
            method.setAccessible(true);
            try {
                Object[] params = new Object[types.length];
                for (int i = 0; i < types.length; i++) {
                    switch (types[i]) {
                        case SENDER -> params[i] = sender;
                        case LABEL -> params[i] = label;
                        case STRING_ARGS -> params[i] = args;
                        case ARGS -> params[i] = new Arguments(args);
                    }
                }
                Object result = method.invoke(obj, params);

                if (result == null) {
                    return true;
                }

                if (result instanceof String text) {
                    sender.sendMessage(ChatUtils.color(text));
                }

                if (result instanceof List list) {
                    if(list.size() > 0) {
                        Object first = list.get(0);
                        if(first instanceof String) {
                            for (Object o : list) {
                                sender.sendMessage(ChatUtils.color((String) o));
                            }
                        }
                    }
                }

                if (result instanceof Boolean bool) {
                    return bool;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    private record BaseCommandData(BaseCommand cmd, List<SubCommandData> subCommands) {

        public boolean execute(CommandSender sender, String label, String[] args) {
            String path = "/";
            HashMap<String, SubCommandData> paths = new HashMap<>();
            for(SubCommandData subCommand : subCommands) {
                String p = subCommand.command.path();
                if(!p.endsWith("/")) {
                    p += "/";
                }
                paths.put(p, subCommand);
            }
            List<String> remaining = null;
            for (int i = 0; i < args.length; i++) {
                String testPath = path + args[i] + "/";
                for (String p : paths.keySet()) {
                    if (p.startsWith(testPath)) {
                        path = testPath;
                        break;
                    }
                }
                if (!path.equals(testPath)) {
                    remaining = Arrays.asList(args).subList(i, args.length);
                    break;
                }
            }
            String[] cmdArgs = remaining == null ? new String[0] : remaining.toArray(new String[0]);

            SubCommandData subCommand = paths.get(path);
            if(!subCommand.command().redirectPath().isBlank()) {
                subCommand.execute(sender, label, cmdArgs);
                String redirect = subCommand.command().redirectPath();
                if(!redirect.endsWith("/")) {
                    redirect += "/";
                }
                subCommand = paths.get(redirect);
            }


            return subCommand.execute(sender, label, cmdArgs);
        }
    }

    protected void process() {
        for(Object obj : objects) {
            Class<?> clazz = obj.getClass();
            if(clazz.isAnnotationPresent(BaseCommand.class)) {
                processBaseCommand(obj, clazz);
            } else {
                processMethods(obj, clazz);
            }
        }
        objects.clear();
    }

    private void processBaseCommand(Object obj, Class<?> clazz) {
        // Collect all classes with @SubCommand annotation
        List<SubCommandData> subCommands = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(SubCommand.class)) {
                SubCommand subCommand = method.getAnnotation(SubCommand.class);
                int[] paramIndexes = new int[4];
                Arrays.fill(paramIndexes, -1);

                List<ParameterType> types = new ArrayList<>();

                int i = 0;
                for(Class<?> param : method.getParameterTypes()) {
                    if(param == CommandSender.class && paramIndexes[0] == -1) {
                        paramIndexes[0] = i;
                        types.add(ParameterType.SENDER);
                    }

                    if(param == String.class && paramIndexes[1] == -1) {
                        paramIndexes[1] = i;
                        types.add(ParameterType.LABEL);
                    }

                    if(param == String[].class && paramIndexes[2] == -1) {
                        paramIndexes[2] = i;
                        types.add(ParameterType.STRING_ARGS);
                    }

                    if(param == Arguments.class && paramIndexes[3] == -1) {
                        paramIndexes[3] = i;
                        types.add(ParameterType.ARGS);
                    }
                    i++;
                }

                SubCommandData data = new SubCommandData(subCommand, method, types.toArray(new ParameterType[0]), obj);
                subCommands.add(data);
            }
        }
        BaseCommandData data = new BaseCommandData(clazz.getAnnotation(BaseCommand.class), subCommands);
        registerBaseCommand(data);
    }

    private void processMethods(Object obj, Class<?> clazz) {
        for(Method m : clazz.getDeclaredMethods()) {
            if(m.isAnnotationPresent(Command.class)) {
                Command annotation = m.getAnnotation(Command.class);

                int[] paramIndexes = new int[4];
                Arrays.fill(paramIndexes, -1);

                List<ParameterType> types = new ArrayList<>();

                int i = 0;
                for(Class<?> param : m.getParameterTypes()) {
                    if(param == CommandSender.class && paramIndexes[0] == -1) {
                        paramIndexes[0] = i;
                        types.add(ParameterType.SENDER);
                    }

                    if(param == String.class && paramIndexes[1] == -1) {
                        paramIndexes[1] = i;
                        types.add(ParameterType.LABEL);
                    }

                    if(param == String[].class && paramIndexes[2] == -1) {
                        paramIndexes[2] = i;
                        types.add(ParameterType.STRING_ARGS);
                    }

                    if(param == Arguments.class && paramIndexes[3] == -1) {
                        paramIndexes[3] = i;
                        types.add(ParameterType.ARGS);
                    }
                    i++;
                }

                CommandData data = new CommandData(m, types.toArray(new ParameterType[0]), obj);
                registerCommand(annotation, data);
            }
        }
    }

    private void registerBaseCommand(BaseCommandData data) {
        BaseCommand cmd = data.cmd();
        if(commands.contains(cmd.name())) {
            throw new IllegalStateException("Command " + cmd.name() + " is already registered!");
        }
        commands.add(cmd.name());
        BukkitCommand command = new BukkitCommand(cmd.name()) {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
                if(cmd.usableBy() != CommandUser.ALL)  {
                    if(cmd.usableBy() == CommandUser.CONSOLE) {
                        if(!(sender instanceof ConsoleCommandSender)) {
                            sender.sendMessage(ChatUtils.color("&cYou must be the console to use this command!"));
                            return true;
                        }
                    }

                    if(cmd.usableBy() == CommandUser.PLAYER) {
                        if(!(sender instanceof Player)) {
                            sender.sendMessage(ChatUtils.color("&cYou must be a player to use this command!"));
                            return true;
                        }
                    }

                    if(cmd.usableBy() == CommandUser.COMMAND_BLOCK) {
                        if(!(sender instanceof BlockCommandSender)) {
                            sender.sendMessage(ChatUtils.color("&cYou must be a command block to use this command!"));
                            return true;
                        }
                    }
                }
                return data.execute(sender, label, args);
            }
        };

        commandMap.register(cmd.prefix(), command);
    }

    private void registerCommand(Command cmd, CommandData data) {
        if(commands.contains(cmd.name())) {
            throw new IllegalStateException("Command " + cmd.name() + " is already registered!");
        }
        commands.add(cmd.name());
        BukkitCommand command = new BukkitCommand(cmd.name()) {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
                if(cmd.usableBy() != CommandUser.ALL)  {
                    if(cmd.usableBy() == CommandUser.CONSOLE) {
                        if(!(sender instanceof ConsoleCommandSender)) {
                            sender.sendMessage(ChatUtils.color("&cYou must be the console to use this command!"));
                            return true;
                        }
                    }

                    if(cmd.usableBy() == CommandUser.PLAYER) {
                        if(!(sender instanceof Player)) {
                            sender.sendMessage(ChatUtils.color("&cYou must be a player to use this command!"));
                            return true;
                        }
                    }

                    if(cmd.usableBy() == CommandUser.COMMAND_BLOCK) {
                        if(!(sender instanceof BlockCommandSender)) {
                            sender.sendMessage(ChatUtils.color("&cYou must be a command block to use this command!"));
                            return true;
                        }
                    }
                }
                return data.execute(sender, label, args);
            }
        };
        command.setDescription(cmd.description());
        command.setUsage(cmd.usage());
        command.setPermission(cmd.permission());
        command.setAliases(List.of(cmd.aliases()));

        commandMap.register(cmd.prefix(), command);
    }

    private boolean canUse(CommandSender sender, CommandUser user) {
        
    }

}
