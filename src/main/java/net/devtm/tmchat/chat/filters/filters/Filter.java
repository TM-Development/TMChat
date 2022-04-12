package net.devtm.tmchat.chat.filters.filters;

import org.bukkit.event.player.AsyncPlayerChatEvent;

public interface Filter {

    boolean process(AsyncPlayerChatEvent event);
    void failed(AsyncPlayerChatEvent event);
    boolean isEnabled();

}
