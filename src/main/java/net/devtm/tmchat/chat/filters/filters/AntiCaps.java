package net.devtm.tmchat.chat.filters.filters;

import net.devtm.tmchat.files.FilesManager;
import net.tmchat.lib.Lib;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AntiCaps implements Filter {

    @Override
    public boolean process(AsyncPlayerChatEvent event) {
        if(event.getPlayer().hasPermission("tmchat.bypass.anticaps")) return false;
        String message = event.getMessage();
        int count = 0;
        for(int i  = 0; i < message.length(); i++) {
            if(Character.isUpperCase(message.charAt(i)))
                count++;
        }
        return count > FilesManager.FILES.getConfig().getConfig().getInt("chat_filters.anti_caps.maxim_caps_on_message");
    }

    @Override
    public void failed(AsyncPlayerChatEvent event) {
        FilesManager.FILES.getConfig().getConfig().getStringList("chat_filters.anti_caps.punishment").forEach(s ->
                Lib.LIB.getComponentBasedAction().process(event.getPlayer(), s, event, "ANTI CAPS"));
    }

    @Override
    public boolean isEnabled() {
        return FilesManager.FILES.getConfig().getConfig().getBoolean("chat_filters.anti_caps.enabled_filter");
    }
}
