package me.ikevoodoo.plugincore.arguments.parsers;

import me.ikevoodoo.plugincore.arguments.ArgumentParser;
import me.ikevoodoo.plugincore.utils.PlayerUtils;
import org.bukkit.entity.Player;

public class PlayerParser implements ArgumentParser<Player> {
    @Override
    public Player parse(String argument) {
        return PlayerUtils.getOnline(argument);
    }
}
