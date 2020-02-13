package com.darktale.darktaleapi.data.serverconfig;

import com.darktale.darktaleapi.data.file.FileManager;
import com.darktale.darktaleapi.data.file.JSONFile;
import com.darktale.darktaleapi.data.file.JSONManager;
import static com.darktale.darktaleapi.data.file.JSONManager.makeJSONFile;
import com.darktale.darktaleapi.data.world.APILocation;

/**
 *
 * @author Ryan
 */
public class ServerConfig {

    private JSONFile jsonFile;
    private APILocation spawnLocation;

    public ServerConfig() {
        jsonFile = new JSONFile(getConfigJSONPath());
        loadJSONVariables();
    }

    private void loadJSONVariables() {
        if (!JSONManager.hasObject(jsonFile, "spawnLocation")) {
            JSONManager.appendJSONObject(jsonFile, 0, "x", "spawnLocation");
            JSONManager.appendJSONObject(jsonFile, 65, "y", "spawnLocation");
            JSONManager.appendJSONObject(jsonFile, 0, "z", "spawnLocation");
        }

        int x = Integer.parseInt((String) JSONManager.getObject(jsonFile, "x", "spawnLocation"));
        int y = Integer.parseInt((String) JSONManager.getObject(jsonFile, "y", "spawnLocation"));
        int z = Integer.parseInt((String) JSONManager.getObject(jsonFile, "z", "spawnLocation"));

        spawnLocation = new APILocation(x, y, z);
    }

    public APILocation getSpawnLocation() {
        return spawnLocation;
    }

    private static String getConfigJSONPath() {

        //Check if the players json file exists
        FileManager.makeDirectory("./DarktaleConfig/");
        FileManager.makeDirectory("./DarktaleConfig/serverconfig");
        String playerJSONPath = "./DarktaleConfig/serverconfig/config.json";

        makeJSONFile(playerJSONPath);

        return playerJSONPath;
    }
}
