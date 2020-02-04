package com.darktale.darktaleapi.data.player;

import com.darktale.darktaleapi.DarktaleAPI;
import com.darktale.darktaleapi.data.file.FileManager;
import com.darktale.darktaleapi.data.file.JSONManager;
import com.darktale.darktaleapi.event.player.APISendPlayerMessageEvent;
import java.util.HashMap;
import org.json.JSONObject;

/**
 *
 * @author Ryan
 */
public class DarktalePlayer {

    private static HashMap<String, DarktalePlayer> darktalePlayers = new HashMap<String, DarktalePlayer>();

    private String playerID;
    private String playerName;
    private String jsonFilePath;
    private JSONObject jsonFile;

    public DarktalePlayer(String playerID, String playerName) {
        this.playerID = playerID;
        this.playerName = playerName;

        try {
            this.jsonFilePath = JSONManager.getPlayerJSONPath(playerID);
            this.jsonFile = new JSONObject(FileManager.readFile(jsonFilePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        darktalePlayers.put(playerID, this);
    }

    public void sendMessage(String message) {
        DarktaleAPI.getAPI().eventHandler().callEvent(new APISendPlayerMessageEvent(playerID, playerName, message));
    }

    public String getID() {
        return playerID;
    }

    public String getName() {
        return playerName;
    }

    public boolean isNew() {
        if (JSONManager.getBoolean(jsonFile, "firstTime")) {
            JSONManager.put(jsonFile, jsonFilePath, "firstTime", false);
        }
        if (!jsonFile.has("firstTime")) {
            JSONManager.put(jsonFile, jsonFilePath, "firstTime", true);
        }

        return jsonFile.getBoolean("firstTime");
    }

    public static DarktalePlayer getPlayer(String playerID, String playerName) {
        if (!darktalePlayers.containsKey(playerID)) {
            DarktalePlayer player = new DarktalePlayer(playerID, playerName);
        }
        return darktalePlayers.get(playerID);
    }
}
