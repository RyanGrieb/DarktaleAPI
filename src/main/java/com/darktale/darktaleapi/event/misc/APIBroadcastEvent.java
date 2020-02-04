package com.darktale.darktaleapi.event.misc;

import com.darktale.darktaleapi.DarktaleAPI;
import com.darktale.darktaleapi.event.APIEvent;

/**
 *
 * @author Ryan
 */
public class APIBroadcastEvent implements APIEvent {

    String message;

    public APIBroadcastEvent(String message) {
        this.message = message;
    }

    @Override
    public boolean execute() {
        DarktaleAPI.getAPI().listenerHandler().callbackEvent(this);

        return true;
    }

    public String getMessage() {
        return message;
    }

}
