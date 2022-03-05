package me.ikevoodoo.omnilib.commands;

import me.ikevoodoo.omnilib.commands.annotations.BaseCommand;
import me.ikevoodoo.omnilib.commands.annotations.Command;
import me.ikevoodoo.omnilib.commands.annotations.SubCommand;
import me.ikevoodoo.omnilib.commands.annotations.arguments.Argument;
import me.ikevoodoo.omnilib.commands.annotations.arguments.OptionalArgument;
import me.ikevoodoo.omnilib.commands.annotations.optionals.AutoComplete;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandParser {

    private static CommandParser instance;

    //private CommandMap commandMap;

    private CommandParser() {
        /*try {
            Method cmdMap = Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap", null);
            cmdMap.setAccessible(true);
            commandMap = (CommandMap) cmdMap.invoke(Bukkit.getServer(), null);
        } catch (Exception e) {
            e.printStackTrace();
            commandMap = null;
        }*/
    }

    public static CommandParser getInstance() {
        if (instance == null) {
            instance = new CommandParser();
        }
        return instance;
    }

    public boolean isComplex(Object obj) {
        return obj.getClass().isAnnotationPresent(BaseCommand.class);
    }

    public RuntimeComplexCommand parseComplex(Object obj) {
        Class<?> clazz = obj.getClass();
        BaseCommand baseCommand = clazz.getAnnotation(BaseCommand.class);
        boolean autoComplete = clazz.isAnnotationPresent(AutoComplete.class);
        return new RuntimeComplexCommand(baseCommand.value(), autoComplete, getSubCommands(obj));
    }

    public List<RuntimeCommand> parseSimple(Object obj) {
        return getCommands(obj);
    }

    private List<RuntimeCommand> getCommands(Object obj) {
        List<RuntimeCommand> commands = new ArrayList<>();

        Class<?> clazz = obj.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Command.class)) {
                Command command = method.getAnnotation(Command.class);
                RuntimeCommand cmd = new RuntimeCommand(command.value(), "", getArgs(method),
                        method.isAnnotationPresent(AutoComplete.class), method, obj);
                commands.add(cmd);
            }
        }

        return commands;
    }

    private List<RuntimeSubcommand> getSubCommands(Object obj) {
        List<RuntimeSubcommand> commands = new ArrayList<>();

        Class<?> clazz = obj.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(SubCommand.class)) {
                SubCommand command = method.getAnnotation(SubCommand.class);
                RuntimeSubcommand cmd = new RuntimeSubcommand(command.value(), "", getArgs(method),
                        method.isAnnotationPresent(AutoComplete.class), command.previous());
                commands.add(cmd);
            }
        }

        return commands;
    }

    private List<RuntimeArgument> getArgs(Method method) {
        List<RuntimeArgument> args = new ArrayList<>();
        int i = 0;
        for(Parameter param : method.getParameters()) {
            if(param.isAnnotationPresent(Argument.class)) {
                Argument arg = param.getAnnotation(Argument.class);
                Class<?> type = param.getType();
                args.add(new RuntimeArgument(i, arg.value(), type, false));
            } else if(param.isAnnotationPresent(OptionalArgument.class)) {
                OptionalArgument arg = param.getAnnotation(OptionalArgument.class);
                Class<?> type = param.getType();
                args.add(new RuntimeArgument(i, arg.value(), type, true));
            }
            i++;
        }
        return args;
    }

    public RuntimeSubcommand tryPick(String command, List<RuntimeSubcommand> subCommands) {
        String[] split = command.split(" ");

        HashMap<String, RuntimeSubcommand> subCommandsMap = new HashMap<>();
        for(RuntimeSubcommand sub : subCommands) {
            subCommandsMap.put(sub.name(), sub);
        }

        String last = null;
        RuntimeSubcommand picked = null;
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            if(subCommandsMap.containsKey(s)) {
                RuntimeSubcommand sub = subCommandsMap.get(s);
                if(sub.args().size() > 0) {
                    boolean stop = false;
                    for (int j = i + 1; j < split.length; j++) {
                        String s2 = split[j];
                        for (RuntimeSubcommand sub2 : subCommands) {
                            if(sub2.prev().equals(sub.name()) && sub2.name().equals(s2) && j <= i + 2 + sub2.args().size()) {
                                picked = sub2;
                                last = sub2.name();
                                stop = true;
                                break;
                            }
                        }
                        if(stop) {
                            break;
                        }
                    }
                    if (stop) continue;
                }
                if(sub.prev() != null && !sub.prev().equals(last)) {
                    last = s;
                    continue;
                }
                picked = sub;
            }
            last = s;
        }
        return picked != null ? picked : subCommandsMap.get(split[0]);
    }

}
