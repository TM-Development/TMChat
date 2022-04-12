package net.devtm.tmchat.files;

import lombok.Getter;
import net.devtm.tmchat.TMChat;
import net.devtm.tmchat.files.files.configFile;
import net.devtm.tmchat.files.files.dataFile;
import net.devtm.tmchat.files.files.localeFile;

@Getter
public enum FilesManager {
    FILES;

    private dataFile data;
    private localeFile locale;
    private configFile config;

    public void initialization() {
        this.config = new configFile(TMChat.PLUGIN.getPlugin());
        this.data = new dataFile(TMChat.PLUGIN.getPlugin());
        loadConfig();
    }
    public void reload() {
        this.data.reloadConfig();
        this.config.reloadConfig();
        this.locale.reloadConfig();
    }
    private void loadConfig() {
        this.data.saveDefaultConfig();
        this.config.saveDefaultConfig();
    }

    public void setLocale(String localize) {
        this.locale = new localeFile(TMChat.PLUGIN.getPlugin(), localize);
        this.locale.saveDefaultConfig();
    }

}
