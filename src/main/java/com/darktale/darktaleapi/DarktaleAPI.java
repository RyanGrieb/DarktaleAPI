package com.darktale.darktaleapi;

import com.darktale.darktaleapi.event.EventHandler;
import com.darktale.darktaleapi.listener.APIListener;

public class DarktaleAPI {

    private static DarktaleAPI api;

    private APIListener apiListener;
    private EventHandler eventHandler;

    public DarktaleAPI() {
        eventHandler = new EventHandler();
        apiListener = new APIListener();
    }

    public EventHandler eventHandler() {
        return eventHandler;
    }

    public APIListener apiListener() {
        return apiListener;
    }

    public static DarktaleAPI getAPI() {
        if (api == null) {
            api = new DarktaleAPI();
        }
        return api;
    }
}
