package net.devtm.tmchat.command;

import net.devtm.tmchat.TMChat;
import net.devtm.tmchat.utils.Common;
import net.md_5.bungee.chat.ComponentSerializer;
import net.tmchat.lib.base.ColorAPI;
import net.tmchat.lib.base.MessageHandler;
import net.tmchat.lib.base.VersionCheckers;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.Level;


public class PluginCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command cmd, @NotNull String s, @NotNull String[] arguments) {

        if(arguments.length > 0) {

            /* Some /tmchat plugin commands */

            if(pluginCommands(commandSender, arguments))
                return true;

            /* SubCommands */

            if (Common.USE.getSubCommands().containsKey(arguments[0])) {
                return Common.USE.getSubCommands().get(arguments[0]).process(commandSender, arguments);
            }
        }

        help(commandSender);
        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] arguments) {

        if(arguments.length > 0) {
            if (Common.USE.getSubCommands().containsKey(arguments[0])) {
                return Common.USE.getSubCommands().get(arguments[0]).tabComplete(commandSender, arguments);
            }
        }

        return null;
    }

    private boolean pluginCommands(@NotNull CommandSender commandSender, @NotNull String[] arguments) {
        if (!commandSender.hasPermission("tmchat.command." + arguments[0])) {
            commandSender.sendMessage(MessageHandler.message("basic.no_permission").prefix().placeholderAPI(commandSender).toStringColor());
            return true;
        }
        switch (arguments[0]) {
            case "reload":
                try {
                    Common.USE.reload();
                    commandSender.sendMessage(MessageHandler.message("commands.plugin.reload.success").prefix().placeholderAPI(commandSender).toStringColor());
                } catch (Exception e) {
                    TMChat.PLUGIN.getPlugin().getLogger().log(Level.INFO, ColorAPI.process("&7(( &cERROR &7)) &cReload command failed: &f" + e.getMessage()));
                }
                return true;
            case "help":
                help(commandSender);
                return true;
            case "info":
                return true;
        }
        return false;
    }

    private void help(CommandSender commandSender) {
        if(!commandSender.hasPermission("tmtokens.command.help")) {
            commandSender.sendMessage(MessageHandler.chat("\n <GRADIENT:#B993D6-#8CA6DB>TMChat</GRADIENT> &7(v" + TMChat.PLUGIN.getPlugin().getDescription().getVersion() + ")").toStringColor());
            commandSender.sendMessage(MessageHandler.message("basic.no_permission").replace("%pl_prefix%", "").toStringColor());
            commandSender.sendMessage("\n");
        } else {
            commandSender.sendMessage(MessageHandler
                    .chat("\n  <GRADIENT:#B993D6-#8CA6DB>TMChat</GRADIENT> &7(v" + TMChat.PLUGIN.getPlugin().getDescription().getVersion() + ")\n \n  &f&nArguments&f: &7[] Required, () Optional." +
                            "\n \n  &#f7971e▸ &7/tmchat chat help or /chat help\n  &#f9a815▸ &7/tmchat messages help or /messages help" +
                            "\n  &#fab011▸ &7/tmchat reload\n  &#fcb90d▸ &7/tmchat info\n" +
                            "\n \n  &#17F7C1▸ &7/tmchat wiki &#17f7c1[L&#17f6c4e&#16f5c6a&#16f4c9r&#16f4cbn &#16f3cem&#15f2d1o&#15f1d3r&#15f0d6e &#14efd8a&#14eedbb&#14edddo&#14ede0u&#13ece3t &#13ebe5t&#13eae8h&#12e9eai&#12e8eds &#12e7f0p&#11e6f2l&#11e6f5u&#11e5f7g&#11e4fai&#10e3fcn&#10e2ff]\n")
                    .toStringColor());

            if (VersionCheckers.getVersion() >= 16) {
                ((Player) commandSender).spigot().sendMessage(ComponentSerializer.parse("[\"\",{\"text\":\"  ▸ Support: \",\"color\":\"#F72B2B\"},{\"text\":\"[Wiki]\",\"color\":\"#F72B2B\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://wiki.devtm.net/tmtokens\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click To Open\"}},{\"text\":\" \",\"color\":\"#F72B2B\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click To Open\"}},{\"text\":\"[Discord]\",\"color\":\"#F72B2B\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://discord.com/invite/XFtV7qgajR\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click To Open\"}}]"));
                ((Player) commandSender).spigot().sendMessage(ComponentSerializer.parse("[\"\",{\"text\":\"  ▸ \",\"color\":\"red\"},{\"text\":\"Plugin: \",\"color\":\"red\"},{\"text\":\"[SpigotMC]\",\"color\":\"red\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.spigotmc.org/resources/authors/xmaikiyt.508656/\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to open\"}}]"));
            } else{
                ((Player) commandSender).spigot().sendMessage(ComponentSerializer.parse("[\"\",{\"text\":\"  ▸ Support: \",\"color\":\"red\"},{\"text\":\"[Wiki]\",\"color\":\"red\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://wiki.devtm.net/tmtokens\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Click to open\"}},{\"text\":\" \",\"color\":\"red\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Click to open\"}},{\"text\":\"[Discord]\",\"color\":\"red\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://discord.com/invite/XFtV7qgajR\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Click to open\"}}]"));
                ((Player) commandSender).spigot().sendMessage(ComponentSerializer.parse("[\"\",{\"text\":\"  ▸\",\"color\":\"red\"},{\"text\":\"Plugin: \",\"color\":\"red\"},{\"text\":\"[SpigotMC]\",\"color\":\"red\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.spigotmc.org/resources/authors/xmaikiyt.508656/\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Click to open\"}}]"));
            }
            commandSender.sendMessage(MessageHandler.chat("\n\n&7&oNote: This plugin is still in the beta stage, if any bugs please report them on our discord server or direct message me on discord (MaikyDev#5343) or make a issues on github! You can find those links on our website!").toStringColor());
        }
    }

}
