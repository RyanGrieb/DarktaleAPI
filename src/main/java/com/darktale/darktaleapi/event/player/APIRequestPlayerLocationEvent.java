package com.darktale.darktaleapi.event.player;

import com.darktale.darktaleapi.DarktaleAPI;

/**
 *
 * @author Ryan
 */
public class APIRequestPlayerLocationEvent extends APIPlayerEvent {

    public APIRequestPlayerLocationEvent(String playerID, String playerName) {
        super(playerID, playerName);
    }

    @Override
    public boolean execute() {
        DarktaleAPI.getAPI().listenerHandler().callbackEvent(this);
        return true;
    }
}
