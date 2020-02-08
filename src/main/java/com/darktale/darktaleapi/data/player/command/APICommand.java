package com.darktale.darktaleapi.data.player.command;

import com.darktale.darktaleapi.data.file.FileManager;
import com.darktale.darktaleapi.data.file.JSONFile;
import static com.darktale.darktaleapi.data.file.JSONManager.makeJSONFile;
import com.darktale.darktaleapi.data.player.DarktalePlayer;
import java.util.HashMap;
import org.json.JSONObject;

/**
 *
 * @author Ryan
 */
public abstract class APICommand {

    protected HashMap<String, APICommand> subCommands;
    private String name;
    private String fullname;

    private JSONFile jsonFile;

    public APICommand(String name, String fullname) {
        this.name = name;
        this.fullname = fullname;
        this.subCommands = new HashMap<String, APICommand>();

        FileManager.makeDirectory("./DarktaleConfig/");
        FileManager.makeDirectory("./DarktaleConfig/commands");

        String jsonFilepath;

        //If we are a parent command with no subcommands
        if (fullname.split(" ").length <= 1) {
            FileManager.makeDirectory("./DarktaleConfig/commands/" + name);
            jsonFilepath = "./DarktaleConfig/commands/" + name + "/" + name + ".json";
        } else {
            //TODO: Account for multiple subcommands. e.g. "/darktale clan create [NAME]"
            String parentCommandName = fullname.split(" ")[0];
            jsonFilepath = "./DarktaleConfig/commands/" + parentCommandName + "/" + fullname + ".json";
        }

        makeJSONFile(jsonFilepath);
        this.jsonFile = new JSONFile(jsonFilepath);

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

    public String getDescription() {
        //TODO: Fix redundancy
        if (!jsonFile.has("description")) {
            jsonFile.put("description", "NONE");
            FileManager.setFileText(jsonFile.getFilePath(), jsonFile.toString());
        }

        return jsonFile.getString("description");
    }

    public String getUsage() {
        //TODO: Fix redundancy
        if (!jsonFile.has("usage")) {
            jsonFile.put("usage", "NONE");
            FileManager.setFileText(jsonFile.getFilePath(), jsonFile.toString());
        }

        return jsonFile.getString("usage");
    }

    public void printHelp(DarktalePlayer player) {
        player.sendMessage("Usage: " + getUsage());
    }

    public abstract void execute(DarktalePlayer player, String[] arguments);

}
