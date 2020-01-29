package com.darktale.darktaleapi.event;

import com.darktale.darktaleapi.DarktaleAPI;

/**
 *
 * @author Ryan
 */
public class EventSendPlayerMessage implements Event {

    private String playerID, message;

    public EventSendPlayerMessage(String playerID, String message) {
        this.playerID = playerID;
        this.message = message;
    }

    @Override
    public void execute() {
        DarktaleAPI.getAPI().apiListener().callListener("apiSendPlayerMessage", this);
    }

    public String getPlayerID() {
        return playerID;
    }

    public String getMessage() {
        return message;
    }
}
