package net.devtm.tmchat;

import lombok.Getter;
import net.devtm.tmchat.chat.ChatModifier;
import net.devtm.tmchat.command.PluginCommand;
import net.devtm.tmchat.command.alias.ChatAlias;
import net.devtm.tmchat.files.FilesManager;
import net.devtm.tmchat.listener.WelcomerListener;
import net.devtm.tmchat.utils.Common;
import net.devtm.tmchat.utils.TMChatCBA;
import net.tmchat.lib.Lib;
import net.tmchat.lib.base.bStatsMetrics;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public enum TMChat {
    PLUGIN;

    private TMChatPlugin plugin;

    /**
     * Start method for the plugin - {@link JavaPlugin}
     *
     * @param plugin the plugin instance
     */
    public void start(final TMChatPlugin plugin) {
        this.plugin = plugin;
        assert plugin != null : "Something went wrong! Plugin was null.";
        FilesManager.FILES.initialization();
        FilesManager.FILES.setLocale(FilesManager.FILES.getConfig().getConfig().getString("select_locale"));
        libSetup();
        Common.USE.startLog();
        Common.USE.setupPlugin();
        usebStats();
        init();
    }

    /**
     * Stop method for the plugin - {@link JavaPlugin}
     *
     * @param plugin the plugin instance
     */
    public void stop(final TMChatPlugin plugin) {
        this.plugin = plugin;
        Common.USE.endPlugin();
        assert plugin != null : "Something went wrong! Plugin was null.";
    }

    /**
     * Register the events and commands
     *
     */
    private void init() {
        /* Events */
        getPlugin().getServer().getPluginManager().registerEvents(new ChatModifier(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents(new WelcomerListener(), getPlugin());
        /* Commands */
        plugin.getCommand("tmchat").setExecutor(new PluginCommand());
        plugin.getCommand("tmchat").setTabCompleter(new PluginCommand());
        plugin.getCommand("chat").setExecutor(new ChatAlias());
    }
    /**
     * Setups my LIB
     *
     */
    private void libSetup() {
        Lib.LIB.libStart(plugin);
        Lib.LIB.setLocales(FilesManager.FILES.getLocale().getConfig());
        Lib.LIB.enableCBA();
        Lib.LIB.getComponentBasedAction().registerMethod(new TMChatCBA());
    }

    /**
     * Setup bStats
     */
    private void usebStats() {
        if(getPlugin().getConfig().getBoolean("allow_bstats")) {
            bStatsMetrics metrics = new bStatsMetrics(getPlugin(), 14912);
        }
    }
}