package com.darktale.darktaleapi.data.player.command;

import com.darktale.darktaleapi.DarktaleAPI;
import com.darktale.darktaleapi.data.file.FileManager;
import com.darktale.darktaleapi.data.file.JSONFile;
import static com.darktale.darktaleapi.data.file.JSONManager.makeJSONFile;
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
        //registerCommand(new LongCommand());

        for (APICommand cmd : commands.values()) {
            initalizeCommandJSON(cmd, cmd.getName());
        }
    }

    public void registerCommand(APICommand command) {
        commands.put(command.getName(), command);
        DarktaleAPI.getAPI().eventHandler().callEvent(new APIRegisterCommandEvent(command));
    }

    public void initalizeCommandJSON(APICommand command, String path) {
        FileManager.makeDirectory("./DarktaleConfig/");
        FileManager.makeDirectory("./DarktaleConfig/commands/");
        FileManager.makeDirectory("./DarktaleConfig/commands/" + path);

        String jsonPath = "./DarktaleConfig/commands/" + path + "/" + command.getName() + ".json";

        makeJSONFile(jsonPath);
        command.setJSON(new JSONFile(jsonPath));

        for (APICommand cmd : command.subCommands.values()) {
            if (cmd.subCommands.size() >= 1) {
                initalizeCommandJSON(cmd, path + "/" + cmd.getName());
            } else {
                initalizeCommandJSON(cmd, path);
            }
        }
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

    /*
        String jsonFilepath;

        if (command.subCommands.size() >= 1) { //E.g. /long command example
            //If we are a parent command with no subcommands
            FileManager.makeDirectory("./DarktaleConfig/commands/" + name + "/" + command.getName());
            jsonFilepath = "./DarktaleConfig/commands/" + getCommandParentDirectory() + "/" + command.getName() + "/" + command.getName() + ".json";
            System.out.println(command.getName() + " is a sub command /w sub commands:");
            //System.out.println("parentDir: " + getCommandParentDirectory());
            System.out.println("path: " + jsonFilepath + "\n");
        } else { //E.g. /long short
            jsonFilepath = "./DarktaleConfig/commands/" + getCommandParentDirectory() + "/" + command.getName() + ".json";
            System.out.println(command.getName() + " is a sub command:");
            //System.out.println("parentDir: " + getCommandParentDirectory());
            System.out.println("path: " + jsonFilepath + "\n");
        }

        //            makeJSONFile(jsonFilepath);
        //   this.jsonFile = new JSONFile(jsonFilepath);
    }*/
}
