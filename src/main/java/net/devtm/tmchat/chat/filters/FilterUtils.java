package net.devtm.tmchat.chat.filters;

import net.devtm.tmchat.chat.filters.filters.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Arrays;
import java.util.List;

public class FilterUtils {

    public void checkFilters(AsyncPlayerChatEvent event) {
        List<Filter> FILTER = Arrays.asList(
                new AntiIP(), new AntiSpam(), new AntiUrls(), new AntiSwear(), new AntiCaps()
        );
        for (Filter filter : FILTER) {
            if(filter.isEnabled()) {
                if (filter.process(event)) {
                    filter.failed(event);
                    return;
                }
            }
        }
    }

}
