package net.devtm.tmchat;

import org.bukkit.plugin.java.JavaPlugin;

public class TMChatPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        TMChat.PLUGIN.start(this);
    }

    @Override
    public void onDisable() {
        TMChat.PLUGIN.stop(this);
    }
}