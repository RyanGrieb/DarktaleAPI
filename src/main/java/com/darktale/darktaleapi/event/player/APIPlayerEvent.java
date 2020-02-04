package com.darktale.darktaleapi.event.player;

import com.darktale.darktaleapi.data.player.DarktalePlayer;
import com.darktale.darktaleapi.event.APIEvent;

/**
 *
 * @author Ryan
 */
public class APIPlayerEvent implements APIEvent {

    protected DarktalePlayer player;

    public APIPlayerEvent(String playerID, String playerName) {
        this.player = DarktalePlayer.getPlayer(playerID, playerName);
    }

    @Override
    public boolean execute() {
        return true;
    }

    public DarktalePlayer getPlayer() {
        return player;
    }
}
