package me.ikevoodoo.omnilib.commands;

import me.ikevoodoo.omnilib.commands.annotations.Command;
import me.ikevoodoo.omnilib.commands.annotations.arguments.Argument;
import me.ikevoodoo.omnilib.commands.annotations.arguments.OptionalArgument;
import me.ikevoodoo.omnilib.commands.annotations.optionals.AutoComplete;

public class Testing {

    @AutoComplete
    @Command("test")
    public void test(@Argument("test") String test, @OptionalArgument("test2") String test2) {
        System.out.println("test");
    }

}
