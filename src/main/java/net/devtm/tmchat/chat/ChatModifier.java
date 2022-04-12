package net.devtm.tmchat.chat;

import net.devtm.tmchat.TMChat;
import net.devtm.tmchat.chat.filters.FilterUtils;
import net.devtm.tmchat.files.FilesManager;
import net.devtm.tmchat.utils.Common;
import net.tmchat.lib.CBA.TMPL;
import net.tmchat.lib.base.MessageHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatModifier implements Listener {

    ChatCommon common;
    FilterUtils filter;

    public ChatModifier () {
        this.common = new ChatCommon();
        this.filter = new FilterUtils();
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onChatEvent(AsyncPlayerChatEvent event) {

        /* Check if chat is muted */
        TMPL tmpl = new TMPL();
        if(Common.USE.isChatMuted() && !event.getPlayer().hasPermission("tmchat.bypass.mutechat")) {
            tmpl.setCode(FilesManager.FILES.getConfig().getConfig().getStringList("mute_chat.on_player_tries_to_send_a_text"));
            tmpl.process(event.getPlayer());
            event.setCancelled(true); return;
        }

        /* Check Filters */
        filter.checkFilters(event);

        /* Check if the event is still opened */
        if(event.isCancelled()) return;

        /* Check for colors and other not allowed stuff */
        String playerMessage;
        if(event.getMessage().contains("&") && !event.getPlayer().hasPermission("tmchat.colors")) {
            playerMessage = event.getMessage().replaceAll("&[0-z]", "");
        } else
            playerMessage = event.getMessage();

        /* Set formatter option */
        String chatMessage = FilesManager.FILES.getConfig().getConfig().getString("chat.format.string");
        Matcher getTheFormatter = Pattern.compile("\\{(.*?)\\}").matcher(chatMessage);
        getTheFormatter.find();
        /* Send Message */
        String realMessage = chatMessage.replaceFirst("\\{"+ getTheFormatter.group(1) + "}", "");
        realMessage = common.formatString(realMessage, event.getPlayer(), playerMessage);
        realMessage = realMessage.replaceFirst("^ *", "");
        TMChat.PLUGIN.getPlugin().getLogger().log(Level.INFO, event.getPlayer().getDisplayName() + ": " + playerMessage);
        Common.USE.getPlayerSpamTime().add(new ChatPlayer(event));
        switch (getTheFormatter.group(1)) {
            case "TEXT":
                event.setFormat(MessageHandler.chat(realMessage).toStringColor());
                break;
            case "CUSTOM":
                event.setCancelled(true);
                for (Player player1 : event.getRecipients()) {
                    player1.spigot().sendMessage(common.buildString(realMessage, event.getPlayer(), playerMessage));
                }
                break;
            case "JSON":
                event.setCancelled(true);
                for (Player player : event.getRecipients()) {
                    player.spigot().sendMessage(common.toJsonMessage(realMessage, event.getPlayer(), playerMessage));
                }
                break;
        }
    }

}
