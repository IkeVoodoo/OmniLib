package me.ikevoodoo.plugincore.arguments;

import me.ikevoodoo.plugincore.utils.AssertUtils;

import java.util.ArrayList;
import java.util.List;

public class Arguments {

    private final String[] args;

    public Arguments(String[] args) {
        this.args = args;
    }

    public boolean isEmpty() {
        return args.length == 0;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public <T> T get(int index, Class<T> type) {
        AssertUtils.assertInBounds(index, args.length, "Index out of bounds");
        ArgumentParser<T> parser = ArgumentParserManager.get(type);
        if (parser != null) {
            return parser.parse(args[index]);
        }
        return null;
    }

    public <T> T getFirst(Class<T> type) {
        for (String arg : args) {
            ArgumentParser<T> parser = ArgumentParserManager.get(type);
            if (parser != null) {
                T out = parser.parse(arg);
                if(out != null) {
                    return out;
                }
            }
        }
        return null;
    }

    public <T> List<T> getAll(Class<T> type) {
        List<T> out = new ArrayList<>();
        for(String arg : args) {
            ArgumentParser<T> parser = ArgumentParserManager.get(type);
            if (parser != null) {
                T parsed = parser.parse(arg);
                if(parsed != null) {
                    out.add(parsed);
                }
            }
        }
        return out;
    }

    /**
     * Behaves exactly like text(0)
     * */
    public String text() {
        return text(0);
    }

    public String text(int toSkip) {
        StringBuilder builder = new StringBuilder();
        for (int i = toSkip; i < args.length; i++) {
            builder.append(args[i]).append(" ");
        }
        return builder.toString().trim();
    }

}
