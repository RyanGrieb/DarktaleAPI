package com.darktale.darktaleapi;

import com.darktale.darktaleapi.data.player.command.APICommandHandler;
import com.darktale.darktaleapi.event.EventHandler;
import com.darktale.darktaleapi.listener.ListenerHandler;

public class DarktaleAPI {

    private static DarktaleAPI api;

    private ListenerHandler listenerHandler;
    private EventHandler eventHandler;
    private APICommandHandler commandHandler;

    public DarktaleAPI() {
        eventHandler = new EventHandler();
        listenerHandler = new ListenerHandler();
        commandHandler = new APICommandHandler();
    }

    public EventHandler eventHandler() {
        return eventHandler;
    }

    public ListenerHandler listenerHandler() {
        return listenerHandler;
    }

    public APICommandHandler commandHandler() {
        return commandHandler;
    }

    public static DarktaleAPI getAPI() {
        if (api == null) {
            api = new DarktaleAPI();
        }
        return api;
    }
}
