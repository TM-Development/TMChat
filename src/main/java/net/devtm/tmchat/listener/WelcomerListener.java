package net.devtm.tmchat.listener;

import net.devtm.tmchat.files.FilesManager;
import net.tmchat.lib.CBA.TMPL;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class WelcomerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(!event.getPlayer().hasPlayedBefore()) {
            switch (FilesManager.FILES.getConfig().getConfig().getString("welcome.modify_on_first_join")) {
                case "disabled":
                    event.setJoinMessage("");
                    break;
                case "custom":
                    event.setJoinMessage("");
                    TMPL tmpl = new TMPL();
                    tmpl.setCode(FilesManager.FILES.getConfig().getConfig().getStringList("welcome.on_first_join")); tmpl.process(event.getPlayer());
                    break;
                default:
                    return;
            }
        }

        /* This will do for the normal join */
        switch (FilesManager.FILES.getConfig().getConfig().getString("welcome.modify_on_join")) {
            case "disabled":
                event.setJoinMessage("");
                break;
            case "custom":
                event.setJoinMessage("");
                TMPL tmpl = new TMPL();
                tmpl.setCode(FilesManager.FILES.getConfig().getConfig().getStringList("welcome.on_join")); tmpl.process(event.getPlayer());
                break;
            default:
                return;
        }

    }

}
