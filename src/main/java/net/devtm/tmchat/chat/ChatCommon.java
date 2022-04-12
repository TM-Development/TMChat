package net.devtm.tmchat.chat;

import net.devtm.tmchat.files.FilesManager;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.chat.ComponentSerializer;
import net.tmchat.lib.base.ColorAPI;
import net.tmchat.lib.base.MessageHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatCommon {

    public Pattern IP_PATTERN = Pattern.compile(FilesManager.FILES.getConfig().getConfig().getString("chat_filters.anti_urls.filter_regex"));
    public Pattern DOMAIN_PATTERN = Pattern.compile(FilesManager.FILES.getConfig().getConfig().getString("chat_filters.anti_ips.filter_regex"));

    public BaseComponent[] buildString(String s, Player sender, String evmessage) {
        String componentJson = null;
        if(s != null) {
            List<String> list = new ArrayList<>(FilesManager.FILES.getConfig().getConfig().getConfigurationSection("chat.format.components").getKeys(false));
            Matcher m = Pattern.compile("\\[(.*?)\\]").matcher(s);
            s = MessageHandler.chat(s).replace("%pl_player%", sender.getName()).placeholderAPI(sender).toStringColor();
            BaseComponent[] message = TextComponent.fromLegacyText(ColorAPI.process(s));
            componentJson = ComponentSerializer.toString(message);
            while (m.find()) {
                if (list.contains(m.group(1))) {
                    componentJson = componentJson.replace("[" + m.group(1) + "]",
                            "\"}," + ComponentSerializer.toString(buildTextComponent(FilesManager.FILES.getConfig().getConfig(),"chat.format.components." + m.group(1), sender))
                    + ",{\"text\":\"");
                    componentJson = componentJson.replace("%pl_message%", evmessage);
                    message = ComponentSerializer.parse("[" + componentJson + "]");
                }
            }
            return message;
        }
        return null;
    }

    /**
     * This method will create a new text component using a configuration and a path from config
     * @param config The configuration
     * @param path the path to all the component things
     * @return
     */
    public BaseComponent[] buildTextComponent(Configuration config, String path, CommandSender commandSender) {

        if(config.contains(path)) {
            try {
                BaseComponent[] message = TextComponent.fromLegacyText(ColorAPI.process(MessageHandler.chat(
                        config.getString(path + ".content")).placeholderAPI(commandSender).toString()));
                for (BaseComponent baseComponent : message) {
                    if(config.contains(path + ".hover")) baseComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            new ComponentBuilder(ColorAPI.process(
                                    MessageHandler.chat(config.getString(path + ".hover")).placeholderAPI(commandSender).toString())).create()));
                    if(config.contains(path + ".open_url")) baseComponent.setClickEvent(new ClickEvent( ClickEvent.Action.OPEN_URL, MessageHandler.chat(config.getString(path + ".open_url")).placeholderAPI(commandSender).toStringColor()));
                    if(config.contains(path + ".run_command")) baseComponent.setClickEvent(new ClickEvent( ClickEvent.Action.RUN_COMMAND, MessageHandler.chat(config.getString(path + ".click")).placeholderAPI(commandSender).toStringColor()));
                    if(config.contains(path + ".suggest_command")) baseComponent.setClickEvent(new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, MessageHandler.chat(config.getString(path + ".suggest_command")).placeholderAPI(commandSender).toStringColor()));
                }
                return message;
            } catch (Exception ignored) {}
        }
        return null;
    }

    public BaseComponent[] toJsonMessage(String s, Player sender, String evmessage) {
        return ComponentSerializer.parse(s);
    }

    public String formatString(String s, Player player, String message) {
        return MessageHandler.chat(s).replace("%pl_message%", message).replace("%pl_player%", player.getName()).placeholderAPI(player).toString();
    }

    public void logPlayer(Player player, String flag) {

    }
}
