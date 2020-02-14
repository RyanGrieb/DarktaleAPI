package com.darktale.darktaleapi.event.player;

import com.darktale.darktaleapi.data.world.APILocation;
import com.darktale.darktaleapi.event.APIEvent;

/**
 *
 * @author Ryan
 */
public class APIRecivePlayerLocationEvent extends APIPlayerEvent {

    private APILocation location;

    public APIRecivePlayerLocationEvent(String playerID, String playerName, APILocation location) {
        super(playerID, playerName);
        this.location = location;
    }

    @Override
    public boolean execute() {
        //TODO: Check when the player walks into territory, restricted areas, ect. we reset the players location by seting a
        //teleport event to the non-updated player location.
        getPlayer().updateLocation(location);
        return true;
    }

}
