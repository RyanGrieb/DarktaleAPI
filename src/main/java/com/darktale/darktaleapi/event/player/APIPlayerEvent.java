package com.darktale.darktaleapi.event.player;

import com.darktale.darktaleapi.data.player.DarktalePlayer;
import com.darktale.darktaleapi.event.APIEvent;

/**
 *
 * @author Ryan
 */
public abstract class APIPlayerEvent implements APIEvent {

    protected DarktalePlayer player;

    public APIPlayerEvent(String playerID, String playerName) {
        this.player = DarktalePlayer.getPlayer(playerID, playerName);
    }

    @Override
    public abstract boolean execute();

    public DarktalePlayer getPlayer() {
        return player;
    }
}
