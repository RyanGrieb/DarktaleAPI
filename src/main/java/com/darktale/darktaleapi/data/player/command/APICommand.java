package com.darktale.darktaleapi.data.player.command;

import com.darktale.darktaleapi.data.file.FileManager;
import com.darktale.darktaleapi.data.file.JSONFile;
import static com.darktale.darktaleapi.data.file.JSONManager.makeJSONFile;
import com.darktale.darktaleapi.data.player.DarktalePlayer;
import java.util.HashMap;

/**
 *
 * @author Ryan
 */
public abstract class APICommand {

    protected HashMap<String, APICommand> subCommands;
    private String name;

    private JSONFile jsonFile;

    public APICommand(String name) {
        this.subCommands = new HashMap<String, APICommand>();
        this.name = name;
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

    public void setJSON(JSONFile jsonFile) {
        this.jsonFile = jsonFile;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, APICommand> getSubCommands() {
        return subCommands;
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
