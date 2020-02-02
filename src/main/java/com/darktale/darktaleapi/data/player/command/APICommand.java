package com.darktale.darktaleapi.data.player.command;

import com.darktale.darktaleapi.data.player.DarktalePlayer;
import java.util.HashMap;

/**
 *
 * @author Ryan
 */
public abstract class APICommand {

    protected HashMap<String, APICommand> subCommands;
    private String name;

    public APICommand(String name) {
        this.name = name;
        this.subCommands = new HashMap<String, APICommand>();
    }

    public void registerSubCommand(APICommand command) {
        subCommands.put(command.getName(), command);
    }

    public boolean executeSubCommand(DarktalePlayer player, String commandName, String[] arguments) {
        if (!subCommands.containsKey(commandName)) {
            return false;
        }

        subCommands.get(commandName).execute(player, arguments);

        return true;
    }

    public String getName() {
        return name;
    }

    public abstract void execute(DarktalePlayer player, String[] arguments);

    public abstract void printHelp(DarktalePlayer player);

}
