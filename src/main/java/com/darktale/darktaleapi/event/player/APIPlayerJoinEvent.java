package com.darktale.darktaleapi.event.player;

import com.darktale.darktaleapi.DarktaleAPI;
import com.darktale.darktaleapi.data.world.APILocation;

public class APIPlayerJoinEvent extends APIPlayerEvent {

    public APIPlayerJoinEvent(String playerID) {
        super(playerID);
    }

    @Override
    public void execute() {
        //Create a first time greeting message & put it into an event the plugin can read
        if (player.isNew()) {
            DarktaleAPI.getAPI().eventHandler().callEvent(new APISendPlayerMessageEvent(player.getPlayerID(), "Welcome to darktale!"));

            //Teleport the player to a spawn location. TODO: Load spawn location from config
            DarktaleAPI.getAPI().eventHandler().callEvent(new APITeleportPlayerEvent(player.getPlayerID(), new APILocation(75, 69, 75)));
        }
    }
}
