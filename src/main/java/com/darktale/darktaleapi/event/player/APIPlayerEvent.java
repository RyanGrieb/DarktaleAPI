package com.darktale.darktaleapi.event.player;

import com.darktale.darktaleapi.event.APIEvent;

/**
 *
 * @author Ryan
 */
public abstract class APIPlayerEvent implements APIEvent {

    protected String playerID;

    public APIPlayerEvent(String playerID) {
        this.playerID = playerID;
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet. Did you not @Override the execute() method?");
    }

    public String getPlayerID() {
        return playerID;
    }
}
