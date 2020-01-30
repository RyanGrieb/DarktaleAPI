package com.darktale.darktaleapi.event.player;

import com.darktale.darktaleapi.DarktaleAPI;
import com.darktale.darktaleapi.data.world.APILocation;

/**
 *
 * @author Ryan
 */
public class APITeleportPlayerEvent extends APIPlayerEvent {

    private APILocation location;

    public APITeleportPlayerEvent(String playerID, APILocation location) {
        super(playerID);
        this.location = location;
    }

    @Override
    public void execute() {
        DarktaleAPI.getAPI().listenerHandler().callbackEvent(this);
    }

    public APILocation getLocation() {
        return location;
    }
}
