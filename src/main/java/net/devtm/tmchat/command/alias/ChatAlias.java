package net.devtm.tmchat.command.alias;

import net.devtm.tmchat.utils.Common;
import net.tmchat.lib.StringUtils.StringTools;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChatAlias implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] arguments) {
        if(arguments.length > 0) {
            return Common.USE.getSubCommands().get("chat").process(commandSender, StringTools.stringConcatenate(new String[]{"undefind"}, arguments));
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] arguments) {
        if(arguments.length > 0) {
            return Common.USE.getSubCommands().get("chat").tabComplete(commandSender, StringTools.stringConcatenate(new String[]{"undefind"}, arguments));
        }
        return null;
    }

}
