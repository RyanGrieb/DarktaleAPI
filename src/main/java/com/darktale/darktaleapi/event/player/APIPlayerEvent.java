package com.darktale.darktaleapi.event.player;

import com.darktale.darktaleapi.event.APIEvent;

/**
 *
 * @author Ryan
 */
public abstract class APIPlayerEvent implements APIEvent {

    String playerID;

    public APIPlayerEvent(String playerID) {
        this.playerID = playerID;
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getPlayerID() {
        return playerID;
    }
}
