package com.darktale.darktaleapi.event.player;

/**
 *
 * @author Ryan
 */
public class APIPlayerLeaveEvent extends APIPlayerEvent {

    public APIPlayerLeaveEvent(String playerID, String playerName) {
        super(playerID, playerName);
    }

    @Override
    public boolean execute() {
        player.saveState();

        return true;
    }

}
