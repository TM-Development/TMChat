package net.devtm.tmchat.utils;

import lombok.Getter;
import net.devtm.tmchat.TMChat;
import net.devtm.tmchat.chat.ChatPlayer;
import net.devtm.tmchat.command.subcommands.ChatSubCommand;
import net.devtm.tmchat.command.subcommands.SubCommand;
import net.devtm.tmchat.files.FilesManager;
import net.tmchat.lib.CBA.TMPL;
import net.tmchat.lib.base.ColorAPI;
import net.tmchat.lib.base.VersionCheckers;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

@Getter
public enum Common {
    USE;

    private List<String> enabledNotifications;
    private final List<Player> chatMutedBypass = new ArrayList<>();
    private final HashMap<String, SubCommand> subCommands = new HashMap<>();
    private List<ChatPlayer> playerSpamTime;

    public void reload() {
        FilesManager.FILES.reload();
    }
    public void setupPlugin() {
        playerSpamTime = new ArrayList<>();
        enabledNotifications = FilesManager.FILES.getData().getConfig().getStringList("notifications_players");
        List<SubCommand> subCommandPrivateList = Arrays.asList(
            new ChatSubCommand()
        );

        for(SubCommand sub : subCommandPrivateList) {
            subCommands.put(sub.getName(), sub);
        }
    }

    public void endPlugin() {
        FilesManager.FILES.getData().getConfig().set("notifications_players", enabledNotifications);
        FilesManager.FILES.getData().saveConfig();
    }

    public void muteChat(String mutedBy, Player player) {
        TMPL tmpl = new TMPL();
        List<String> result = new ArrayList<>();
        if (isChatMuted()) {
            FilesManager.FILES.getConfig().getConfig().getStringList("mute_chat.on_unmute").forEach(s -> result.add(s.replace("%pl_player%", mutedBy)));
            tmpl.setCode(result);
            tmpl.process(player);
            FilesManager.FILES.getData().getConfig().set("chat_muted.muted", false);
        } else {
            FilesManager.FILES.getConfig().getConfig().getStringList("mute_chat.on_mute").forEach(s -> result.add(s.replace("%pl_player%", mutedBy)));
            tmpl.setCode(result);
            tmpl.process(player);
            FilesManager.FILES.getData().getConfig().set("chat_muted.muted", true);
        }
        FilesManager.FILES.getData().saveConfig();
    }

    public void clearChat(String cleardBy, Player player) {
        Bukkit.getOnlinePlayers().forEach(player2 -> {
            if(!player2.hasPermission("tmchat.bypass.clearchat"))
                player2.sendMessage(StringUtils.repeat(" \n", 100));
        });
        TMPL tmpl = new TMPL();
        List<String> result = new ArrayList<>();
        FilesManager.FILES.getConfig().getConfig().getStringList("clear_chat.on_clear").forEach(s -> result.add(s.replace("%pl_player%", cleardBy)));
        tmpl.setCode(result);
        tmpl.process(player);

    }

    public boolean isChatMuted() {
        return FilesManager.FILES.getData().getConfig().getBoolean("chat_muted.muted");
    }

    public void startLog() {
        TMChat.PLUGIN.getPlugin().getLogger().log(Level.INFO, ColorAPI.process("&aLoading TMChat"));
        TMChat.PLUGIN.getPlugin().getLogger().log(Level.INFO, ColorAPI.process("&aHooking into other plugins"));

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null)
            TMChat.PLUGIN.getPlugin().getLogger().log(Level.WARNING, ColorAPI.process("&6PlaceholderAPI is not on the server or not enabled! (Placeholder support is disabled)."));
        else TMChat.PLUGIN.getPlugin().getLogger().log(Level.INFO, ColorAPI.process("&fPlaceholderAPI is supported."));

        if (Bukkit.getPluginManager().getPlugin("Vault") == null)
            TMChat.PLUGIN.getPlugin().getLogger().log(Level.WARNING, ColorAPI.process("&6Vault is not on the server or not enabled!  (Vault support is disabled)."));
        else TMChat.PLUGIN.getPlugin().getLogger().log(Level.INFO, ColorAPI.process("&fVault is supported."));

        TMChat.PLUGIN.getPlugin().getLogger().log(Level.INFO, ColorAPI.process("&aChecking version (Will take a while)..."));
        new VersionCheckers(TMChat.PLUGIN.getPlugin(), 85005).getUpdate(version -> {
            if (TMChat.PLUGIN.getPlugin().getDescription().getVersion().equals(version)) {
                TMChat.PLUGIN.getPlugin().getLogger().log(Level.INFO, ColorAPI.process("&7(( &a✔ &7)) &aRunning latest build &7&o(" + version + ")"));
            } else {
                TMChat.PLUGIN.getPlugin().getLogger().log(Level.WARNING, ColorAPI.process("&7(( &c✘ &7)) &cRunning an old build &7&o(" + TMChat.PLUGIN.getPlugin().getDescription().getVersion()
                        + ") &cLatest build is &7&o(" + version + ")"));
            }
            TMChat.PLUGIN.getPlugin().getLogger().log(Level.INFO, ColorAPI.process("&fMade with love by &cRom&eani&bans"));
        });
    }

}
