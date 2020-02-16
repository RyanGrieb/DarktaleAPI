package com.darktale.darktaleapi.data.player.command;

import com.darktale.darktaleapi.DarktaleAPI;
import com.darktale.darktaleapi.data.player.DarktalePlayer;
import com.darktale.darktaleapi.data.player.command.clan.ClanCommand;
import com.darktale.darktaleapi.data.player.command.serverconfig.SetSpawnCommand;
import com.darktale.darktaleapi.event.command.APIRegisterCommandEvent;
import java.util.HashMap;

/**
 *
 * @author Ryan
 */
public class APICommandHandler {

    /**
     * Command: Can have sub parameters sub parameters can also have sub parameters
     */
    private HashMap<String, APICommand> commands;

    public APICommandHandler() {
        //TODO: Note that we have to account for subcommands
        commands = new HashMap<String, APICommand>();

        registerCommand(new ClanCommand());
        registerCommand(new SetSpawnCommand());
    }

    public void registerCommand(APICommand command) {
        commands.put(command.getName(), command);
        DarktaleAPI.getAPI().eventHandler().callEvent(new APIRegisterCommandEvent(command));
    }

    public void executeCommand(DarktalePlayer player, String commandLine) {
        //Note: the commandLine string contains the full command line & arguments.
        String[] arguments = commandLine.split(" ");

        //Remove any command indications, e.g. '/ or -'
        if (arguments[0].charAt(0) == '/') {
            arguments[0] = arguments[0].substring(1);
        }

        if (!commands.containsKey(arguments[0])) {
            player.sendMessage("Error: Commmand not found");
            return;
        }

        commands.get(arguments[0]).execute(player, arguments);
    }

}
