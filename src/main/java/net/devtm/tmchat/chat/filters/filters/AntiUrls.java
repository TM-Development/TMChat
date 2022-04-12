package net.devtm.tmchat.chat.filters.filters;

import net.devtm.tmchat.files.FilesManager;
import net.tmchat.lib.Lib;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Locale;

public class AntiUrls  implements Filter {
    @Override
    public boolean process(AsyncPlayerChatEvent event) {
        if(event.getPlayer().hasPermission("tmchat.bypass.antiadvertisments")) return false;
        return event.getMessage().toLowerCase(Locale.ROOT).replaceAll(" ", "").matches(
                FilesManager.FILES.getConfig().getConfig().getString("chat_filters.anti_urls.filter_regex")
        );
    }

    @Override
    public void failed(AsyncPlayerChatEvent event) {
        FilesManager.FILES.getConfig().getConfig().getStringList("chat_filters.anti_urls.punishment").forEach(s ->
                Lib.LIB.getComponentBasedAction().process(event.getPlayer(), s, event, "ANTI URL"));
    }

    @Override
    public boolean isEnabled() {
        return FilesManager.FILES.getConfig().getConfig().getBoolean("chat_filters.anti_urls.enabled_filter");
    }
}