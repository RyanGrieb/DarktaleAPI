package com.darktale.darktaleapi;

import com.darktale.darktaleapi.data.player.command.APICommandHandler;
import com.darktale.darktaleapi.debug.DebugCommandListener;
import com.darktale.darktaleapi.event.EventHandler;
import com.darktale.darktaleapi.event.player.APIPlayerCommandEvent;
import com.darktale.darktaleapi.listener.ListenerHandler;

public class DarktaleAPI {

    private static DarktaleAPI api;

    private ListenerHandler listenerHandler;
    private EventHandler eventHandler;
    private APICommandHandler commandHandler;

    public DarktaleAPI() {

    }

    public void setListenerHandler(ListenerHandler listenerHandler) {
        this.listenerHandler = listenerHandler;
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void setCommandHandler(APICommandHandler commandHandler) {
        this.commandHandler = commandHandler;
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

    public static void setAPI(DarktaleAPI darktaleAPI) {
        api = darktaleAPI;
    }

    public static DarktaleAPI getAPI() {
        if (api == null) {
            api = new DarktaleAPI();
        }

        return api;
    }

    public static void main(String[] args) {

        //Register the api handlers
        DarktaleAPI.getAPI().setListenerHandler(new ListenerHandler());

        DarktaleAPI.getAPI().listenerHandler().registerListener("debugCommandListener", new DebugCommandListener());

        DarktaleAPI.getAPI().setEventHandler(new EventHandler());
        DarktaleAPI.getAPI().setCommandHandler(new APICommandHandler());
        //The problem was that the command handler was calling listeners that wernt defined yet.

        boolean cancelled = DarktaleAPI.getAPI().eventHandler().callEvent(
                new APIPlayerCommandEvent("Rhin_", "/clan"));
    }
}
