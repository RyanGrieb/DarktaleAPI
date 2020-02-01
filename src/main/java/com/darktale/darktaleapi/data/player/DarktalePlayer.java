package com.darktale.darktaleapi.data.player;

import com.darktale.darktaleapi.data.file.FileManager;
import com.darktale.darktaleapi.data.file.JSONManager;
import java.util.HashMap;
import org.json.JSONObject;

/**
 *
 * @author Ryan
 */
public class DarktalePlayer {

    private static HashMap<String, DarktalePlayer> darktalePlayers = new HashMap<String, DarktalePlayer>();

    private String playerID;
    private String jsonFilePath;
    private JSONObject jsonFile;

    public DarktalePlayer(String playerID) {
        this.playerID = playerID;

        try {
            this.jsonFilePath = JSONManager.getPlayerJSONPath(playerID);
            this.jsonFile = new JSONObject(FileManager.readFile(jsonFilePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        darktalePlayers.put(playerID, this);
    }

    boolean isFirstTimePlayer() {
        if (JSONManager.getBoolean(jsonFile, "firstTime")) {
            JSONManager.put(jsonFile, jsonFilePath, "firstTime", false);
        }
        if (!jsonFile.has("firstTime")) {
            JSONManager.put(jsonFile, jsonFilePath, "firstTime", true);
        }

        return jsonFile.getBoolean("firstTime");
    }

    public static DarktalePlayer getPlayerFromID(String playerID) {
        if (!darktalePlayers.containsKey(playerID)) {
            DarktalePlayer player = new DarktalePlayer(playerID);
        }
        return darktalePlayers.get(playerID);
    }
}
