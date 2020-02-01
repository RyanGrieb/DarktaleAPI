package com.darktale.darktaleapi.data.file;

import java.io.File;
import org.json.JSONObject;

/**
 *
 * @author Ryan
 */
public class JSONManager {

    public static String getPlayerJSONPath(String playerID) throws Exception {

        //Check if the players json file exists
        FileManager.makeDirectory("./DarktaleConfig/");
        FileManager.makeDirectory("./DarktaleConfig/player");

        String playerJSONPath = "./DarktaleConfig/player/" + playerID + ".json";

        makeJSONFile(playerJSONPath);

        return playerJSONPath;
    }

    public static void makeJSONFile(String filePath) {
        File file = new File(filePath);

        if (!file.isFile()) {
            try {
                file.createNewFile();
                FileManager.setFileText(filePath, "{}");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean getBoolean(JSONObject json, String key) {
        if (!json.has(key)) {
            return false;
        }

        return json.getBoolean(key);
    }

    public static void put(JSONObject json, String jsonPath, String key, Object object) {
        json.put("firstTime", object);
        FileManager.setFileText(jsonPath, json.toString());
    }
}
