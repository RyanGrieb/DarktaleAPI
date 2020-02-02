package com.darktale.darktaleapi.event.command;

import com.darktale.darktaleapi.DarktaleAPI;
import com.darktale.darktaleapi.data.player.command.APICommand;
import com.darktale.darktaleapi.event.APIEvent;

/**
 *
 * @author Ryan
 */
public class APIRegisterCommandEvent implements APIEvent {

    APICommand command;

    public APIRegisterCommandEvent(APICommand command) {
        this.command = command;
    }

    @Override
    public boolean execute() {
        DarktaleAPI.getAPI().listenerHandler().callbackEvent(this);

        return true;
    }

    public APICommand getCommand() {
        return command;
    }

}
