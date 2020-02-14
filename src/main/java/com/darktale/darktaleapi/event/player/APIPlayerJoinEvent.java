package com.darktale.darktaleapi.event.player;

import com.darktale.darktaleapi.DarktaleAPI;
import com.darktale.darktaleapi.data.player.DarktalePlayer;

public class APIPlayerJoinEvent extends APIPlayerEvent {

    public APIPlayerJoinEvent(String playerID, String playerName) {
        super(playerID, playerName);
    }

    @Override
    public boolean execute() {
        //Create a first time greeting message & put it into an event the plugin can read
        if (player.isNew()) {
            player.sendMessage("Welcome to darktale!");

            //Teleport the player to a spawn location.
            player.teleport(DarktaleAPI.getAPI().getServerConfig().getSpawnLocation());
        }

        return true;
    }
}
