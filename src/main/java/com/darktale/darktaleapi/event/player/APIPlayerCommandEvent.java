package com.darktale.darktaleapi.event.player;

import com.darktale.darktaleapi.DarktaleAPI;

/**
 *
 * @author Ryan
 */
public class APIPlayerCommandEvent extends APIPlayerEvent {

    private String command;

    public APIPlayerCommandEvent(String playerID, String command) {
        super(playerID);
        this.command = command;
    }

    @Override
    public boolean execute() {
        System.out.println("APIPlayerCommandEvent");
        DarktaleAPI.getAPI().commandHandler().executeCommand(player, command);

        return false;
    }

}
