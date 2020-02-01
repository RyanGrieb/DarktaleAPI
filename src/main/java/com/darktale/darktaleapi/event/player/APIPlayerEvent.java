package com.darktale.darktaleapi.event.player;

import com.darktale.darktaleapi.data.player.DarktalePlayer;
import com.darktale.darktaleapi.event.APIEvent;

/**
 *
 * @author Ryan
 */
public abstract class APIPlayerEvent implements APIEvent {

    protected DarktalePlayer player;

    public APIPlayerEvent(String playerID) {
        this.player = DarktalePlayer.getPlayerFromID(playerID);
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet. Did you not @Override the execute() method?");
    }

    public DarktalePlayer getPlayer() {
        return player;
    }
}
