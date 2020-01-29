package com.darktale.darktaleapi.event;

import com.darktale.darktaleapi.DarktaleAPI;
import java.util.UUID;

public class EventPlayerJoin implements Event {

    private String playerID;

    public EventPlayerJoin(UUID playerID) {
        this.playerID = playerID.toString();
    }

    @Override
    public void execute() {
        //Create a greeting message & put it into an event the plugin can read
        DarktaleAPI.getAPI().eventHandler().callEvent(new EventSendPlayerMessage(playerID, "Welcome to darktale!"));

        //Teleport the player to a spawn location
    }

    public String getPlayerID() {
        return playerID;
    }
}
