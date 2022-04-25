package me.ikevoodoo.omnilib.input;

import org.bukkit.Player;
import org.bukkit.ChatColor;

public class ChatInput {
    private ChatInput() {}

    private List<Player> players;

    private static void askQuestion(String question, Player player) {
        player.sendMessage(question);
        player.sendMessage(ChatColor.GREEN + "Type a message in chat to reply!")
        
    }
}
