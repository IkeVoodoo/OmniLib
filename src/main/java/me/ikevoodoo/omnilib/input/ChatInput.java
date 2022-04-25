package me.ikevoodoo.omnilib.input;

import org.bukkit.Player;
import org.bukkit.ChatColor;

public class ChatInput {
    private ChatInput() {}

    private static final List<Player> players = new ArrayList<>();

    private static void askQuestion(String question, Player player) {
        player.sendMessage(question);
        player.sendMessage(ChatColor.GREEN + "Type a message in chat to reply!")
        players.add(player);
    }
}
