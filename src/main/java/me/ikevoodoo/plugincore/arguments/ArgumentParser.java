package me.ikevoodoo.plugincore.arguments;

public interface ArgumentParser<T> {

    /**
     * Returns the parsed argument.
     * If the argument is not valid, this method should return null.
     * */
    public T parse(String argument);

}
