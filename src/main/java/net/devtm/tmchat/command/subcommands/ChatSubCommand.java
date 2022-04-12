package net.devtm.tmchat.command.subcommands;

import net.devtm.tmchat.TMChat;
import net.devtm.tmchat.utils.Common;
import net.tmchat.lib.base.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.*;

public class ChatSubCommand implements SubCommand {

    @Override
    public String getName() {
        return "chat";
    }

    @Override
    public boolean process(CommandSender commandSender, String[] arguments) {

        if (!commandSender.hasPermission("tmchat.command.chat." + arguments[1])) {
            commandSender.sendMessage(MessageHandler.message("basic.no_permission").prefix().placeholderAPI(commandSender).toStringColor());
            return true;
        }
        switch (arguments[1].toLowerCase(Locale.ROOT)) {
            case "help":
                commandSender.sendMessage(MessageHandler
                        .chat("\n<GRADIENT:#B993D6-#8CA6DB>CHAT HELP</GRADIENT> \n \n  &f&nArguments&f: &7[] Required, () Optional." +
                                "\n \n  &#f7971e▸ &7/chat mute \n  &#f9a815▸ &7/chat clear" +
                                "\n  &#fab011▸ &7/chat notifications\n  &#fcb90d▸ &7/chat fakemessage [player] [message]\n\n&7").toStringColor());
                break;
            case "mute":
                Common.USE.muteChat(commandSender.getName(), (Player) commandSender);
                break;
            case "notifications":
                if(!Common.USE.getEnabledNotifications().contains(commandSender.getName())) {
                    Common.USE.getEnabledNotifications().add(commandSender.getName());
                    commandSender.sendMessage(MessageHandler.message("commands.chat.notifications.success.enabled").prefix().placeholderAPI(commandSender).toStringColor());
                } else {
                    Common.USE.getEnabledNotifications().remove(commandSender.getName());
                    commandSender.sendMessage(MessageHandler.message("commands.chat.notifications.success.disabled").prefix().placeholderAPI(commandSender).toStringColor());
                }
                break;
            case "clear":
                Common.USE.clearChat(commandSender.getName(), (Player) commandSender);
                break;
            case "fakemessage":
                Set<Player> haha = new HashSet<Player>(Bukkit.getOnlinePlayers());
                StringBuilder sb = new StringBuilder();
                if(arguments.length < 4) {commandSender.sendMessage(MessageHandler.message("commands.chat.fakemessage.help").prefix().placeholderAPI(commandSender).toStringColor()); break;}
                for(int i = 3; i < arguments.length; i++) {
                    sb.append(arguments[i]).append(" ");
                }
                Bukkit.getScheduler().runTaskAsynchronously(TMChat.PLUGIN.getPlugin(), () -> {
                    Bukkit.getPluginManager().callEvent(new AsyncPlayerChatEvent(true, Bukkit.getPlayer(arguments[2]), sb.toString(), haha));
                });
                break;
            }

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender commandSender, String[] arguments) {
        switch (arguments.length) {
            case 2:
                List<String> a = new ArrayList<>();
                for(String s : Arrays.asList("notifications", "mute", "clear", "fakemessage", "help"))
                    if(commandSender.hasPermission("tmchat.command.chat." + s)) a.add(s);
                return a;
            case 3:
                if(arguments[2].equalsIgnoreCase("fakemessage")) {
                    List<String> list = new ArrayList<>();
                    Bukkit.getOnlinePlayers().forEach(pl -> list.add(pl.getName()));
                    return list;
                }
            case 4:
                return null;
        }
        return null;
    }
}
