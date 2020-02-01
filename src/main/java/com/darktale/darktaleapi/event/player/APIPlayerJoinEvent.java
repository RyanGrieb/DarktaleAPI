package com.darktale.darktaleapi.event.player;

import com.darktale.darktaleapi.DarktaleAPI;
import com.darktale.darktaleapi.data.world.APILocation;

public class APIPlayerJoinEvent extends APIPlayerEvent {

    public APIPlayerJoinEvent(String playerID) {
        super(playerID);
    }

    @Override
    public void execute() {
        //Create a greeting message & put it into an event the plugin can read
        DarktaleAPI.getAPI().eventHandler().callEvent(new APISendPlayerMessageEvent(playerID, "Welcome to darktale!"));

        //Teleport the player to a spawn location. TODO: Load spawn location from config
        DarktaleAPI.getAPI().eventHandler().callEvent(new APITeleportPlayerEvent(playerID, new APILocation(75, 69, 75)));
    }
}
