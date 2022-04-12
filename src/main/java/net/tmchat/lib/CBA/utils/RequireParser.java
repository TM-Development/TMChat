package net.tmchat.lib.CBA.utils;

import net.tmchat.lib.Lib;
import net.tmchat.lib.base.ColorAPI;
import net.tmchat.lib.base.MessageHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;

public class RequireParser {

    ClickType clickType;

    public RequireParser() {}

    public boolean getResult(String[] args, Player player) {
        return compile(args, player);
    }

    private boolean compile(String[] arguments, Player player) {
        switch (arguments[0].toLowerCase(Locale.ROOT)) {
            case "javascript":
                break;
            case "expression":
                return expressionParser(player, arguments[1], arguments[2], arguments[3]);
            case "permission":
                return player.hasPermission(arguments[1]);
            case "xp":
                if (player.getLevel() >= Integer.parseInt(arguments[1])) {
                    return true;
                }
                break;
            case "!permission":
                return !player.hasPermission(arguments[1]);
            case "click_type":
                if(clickType != null)
                    return ClickType.valueOf(arguments[1]).equals(clickType);
                else return true;
        }
        Lib.LIB.getPlugin().getLogger().log(Level.WARNING, "We found a problem trying to compile you`re requirement: " + arguments[0]);
        return false;
    }

    private boolean expressionParser(Player player, String operation1, String operator, String operation2) {
        String op1 = MessageHandler.chat(operation1).placeholderAPI(player).toString();
        String op2 = MessageHandler.chat(operation2).placeholderAPI(player).toString();
        try {
            switch (operator) {
                case "==":
                    return Objects.equals(op1, op2);
                case "!=":
                    return !Objects.equals(op1, op2);
                case ">=":
                    return Double.parseDouble(op1) >= Double.parseDouble(op2);
                case "<=":
                    return Integer.parseInt(op1) <= Integer.parseInt(op2);
            }
        } catch (Exception e) {
            Lib.LIB.getPlugin().getLogger().log(Level.INFO, ColorAPI.process("We found a problem trying to compile you`re expression: "));
        }
        return false;
    }
    public RequireParser provideClickType(ClickType clickType) {
        this.clickType = clickType;
        return this;
    }

}
