package net.devtm.tmchat.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.tmchat.lib.CBA.CBAMethods;
import net.tmchat.lib.Lib;
import net.tmchat.lib.base.ColorAPI;
import net.tmchat.lib.base.MessageHandler;
import net.tmchat.lib.base.VersionCheckers;
import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

public class TMChatCBA implements CBAMethods {

    List<String> comp = Arrays.asList("notify_staff");

    @Override
    public void process(Player player, String component, Object... obj) {
        if(obj[0] instanceof AsyncPlayerChatEvent) {
            String actionContent = "";
            if (component.split("]").length > 1)
                actionContent = component.split("]")[1].replaceFirst("^ *", "");

            switch (component.substring(component.indexOf("[") + 1, component.indexOf("]")).toLowerCase(Locale.ROOT)) {
                case "notify_staff":
                    Common.USE.getEnabledNotifications().forEach(pl -> {
                        if (Bukkit.getPlayer(pl) != null) {
                            Bukkit.getPlayer(pl).sendMessage(MessageHandler.message("more.staff_notification").placeholderAPI(((AsyncPlayerChatEvent) obj[0]).getPlayer())
                                    .replace("%pl_message%", ((AsyncPlayerChatEvent) obj[0]).getMessage()).replace("%pl_alert%", (String) obj[1]).toStringColor());
                        }
                    });
                    break;
                default:
                    Lib.LIB.getPlugin().getLogger().log(Level.SEVERE, ColorAPI.process("&cTMPL Error on a command because ACTION type not exists on command! Command: &f"));
                    break;
            }
        }
    }

    @Override
    public List<String> getComponents() {
        return comp;
    }
}
