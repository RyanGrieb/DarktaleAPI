package com.darktale.darktaleapi.event.player;

import com.darktale.darktaleapi.DarktaleAPI;
import com.darktale.darktaleapi.data.world.APILocation;

/**
 *
 * @author Ryan
 */
public class APITeleportPlayerEvent extends APIPlayerEvent {

    private APILocation location;

    public APITeleportPlayerEvent(String playerID, String playerName, APILocation location) {
        super(playerID, playerName);
        this.location = location;
    }

    @Override
    public boolean execute() {
        DarktaleAPI.getAPI().listenerHandler().callbackEvent(this);

        return true;
    }

    public APILocation getLocation() {
        return location;
    }
}
