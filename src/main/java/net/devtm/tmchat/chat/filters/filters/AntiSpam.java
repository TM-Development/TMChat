package net.devtm.tmchat.chat.filters.filters;

import net.devtm.tmchat.chat.ChatPlayer;
import net.devtm.tmchat.files.FilesManager;
import net.devtm.tmchat.utils.Common;
import net.tmchat.lib.Lib;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AntiSpam implements Filter {
    @Override
    public boolean process(AsyncPlayerChatEvent event) {
        int count = 0;
        List<ChatPlayer> chatHolder = new ArrayList<>();
        for(Iterator<ChatPlayer> iterator = Common.USE.getPlayerSpamTime().iterator(); iterator.hasNext();) {
            ChatPlayer chatPlayer = iterator.next();
            if(chatPlayer.player.equals(event.getPlayer())) {
                chatHolder.add(chatPlayer);
            }
            if(chatPlayer.player.equals(event.getPlayer()) && (chatPlayer.timeSent + (FilesManager.FILES.getConfig().getConfig().getInt("chat_filters.anti_spam.maxim_words_in_seconds") * 1000) > (System.currentTimeMillis()))) {
                count++;
            }
        }
        if(count == 0)
            Common.USE.getPlayerSpamTime().removeAll(chatHolder);

        return count >= FilesManager.FILES.getConfig().getConfig().getInt("chat_filters.anti_spam.maxim_words");
    }

    @Override
    public void failed(AsyncPlayerChatEvent event) {
        FilesManager.FILES.getConfig().getConfig().getStringList("chat_filters.anti_spam.punishment").forEach(s ->
                Lib.LIB.getComponentBasedAction().process(event.getPlayer(), s, event, "ANTI SPAM"));
    }

    @Override
    public boolean isEnabled() {
        return FilesManager.FILES.getConfig().getConfig().getBoolean("chat_filters.anti_spam.enabled_filter");
    }

}
