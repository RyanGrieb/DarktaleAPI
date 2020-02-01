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
    public void execute() {
        DarktaleAPI.getAPI().listenerHandler().callbackEvent(this);
    }

    public String getMessage() {
        return message;
    }
}
