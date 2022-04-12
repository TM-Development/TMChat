package net.devtm.tmchat.chat.filters.filters;

import net.devtm.tmchat.files.FilesManager;
import net.tmchat.lib.Lib;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Locale;

public class AntiSwear implements Filter {

    @Override
    public boolean process(AsyncPlayerChatEvent event) {
        if(event.getPlayer().hasPermission("tmchat.bypass.antiswear")) return false;
        String message = event.getMessage().toLowerCase(Locale.ROOT);
        for(String s : FilesManager.FILES.getConfig().getConfig().getStringList("chat_filters.anti_swear.bad_words")) {
            if(message.replaceAll(" ", "").contains(s))
                return true;
        }
        return false;
    }

    @Override
    public void failed(AsyncPlayerChatEvent event) {
        FilesManager.FILES.getConfig().getConfig().getStringList("chat_filters.anti_swear.punishment").forEach(s ->
                Lib.LIB.getComponentBasedAction().process(event.getPlayer(), s, event, "ANTI SWEAR"));
    }

    @Override
    public boolean isEnabled() {
        return FilesManager.FILES.getConfig().getConfig().getBoolean("chat_filters.anti_swear.enabled_filter");
    }
}
