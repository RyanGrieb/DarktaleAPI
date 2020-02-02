package com.darktale.darktaleapi.event.player;

import com.darktale.darktaleapi.DarktaleAPI;

/**
 *
 * @author Ryan
 */
public class APISendPlayerMessageEvent extends APIPlayerEvent {

    private String message;

    public APISendPlayerMessageEvent(String playerID, String message) {
        super(playerID);
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
