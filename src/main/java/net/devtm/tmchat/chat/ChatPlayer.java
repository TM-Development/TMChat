package net.devtm.tmchat.chat;

import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatPlayer {

    public Player player;
    public String message;
    public Long timeSent;

    public ChatPlayer(AsyncPlayerChatEvent event) {
        this.player = event.getPlayer();
        this.message = event.getMessage();
        this.timeSent = System.currentTimeMillis();
    }

}
