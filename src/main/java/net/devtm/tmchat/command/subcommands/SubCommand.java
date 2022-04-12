package net.devtm.tmchat.command.subcommands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubCommand {

    /**
     * The arguments start from <font color=red>1</font>
     */
    boolean process(CommandSender commandSender, String[] arguments);

    List<String> tabComplete(CommandSender commandSender, String[] arguments);

    String getName();
}
