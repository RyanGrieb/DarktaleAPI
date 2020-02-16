package com.darktale.darktaleapi.event.player;

import com.darktale.darktaleapi.DarktaleAPI;

/**
 *
 * @author Ryan
 */
public class APIPlayerCommandEvent extends APIPlayerEvent {

    private String command;

    public APIPlayerCommandEvent(String playerID, String playerName, String command) {
        super(playerID, playerName);
        this.command = command;
    }

    @Override
    public boolean execute() {
        DarktaleAPI.getAPI().commandHandler().executeCommand(player, command);
        return true;
    }

}
