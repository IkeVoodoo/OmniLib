package me.ikevoodoo.omnilib.commands;

import me.ikevoodoo.omnilib.commands.annotations.BaseCommand;
import me.ikevoodoo.omnilib.commands.annotations.SubCommand;
import me.ikevoodoo.omnilib.commands.annotations.arguments.Argument;
import me.ikevoodoo.omnilib.commands.annotations.optionals.AutoComplete;


/**
 * /rank
 * /rank give <player> <rank>
 * /rank take <player> <rank>
 * /rank info
 * /rank info list
 * */
@AutoComplete
@BaseCommand("rank")
public class Testing2 {

    @SubCommand("give")
    private String give(@Argument("rank") String rank) {
        // Do something...
        return "&aDone!";
    }

    @SubCommand("take")
    private String take(@Argument("rank") String rank) {
        // Do something...
        return "&aDone!";
    }

    @SubCommand("info")
    private String info(@Argument("rank") String rank) {
        // Do something...
        return "&aDone!";
    }

    @SubCommand(value = "list", previous = "info")
    private String list() {
        // Do something...
        return "&aDone!";
    }

    //@SubCommand(value = "list1", previous = "list")
    private String list1() {
        // Do something...
        return "&aDone!";
    }
}
