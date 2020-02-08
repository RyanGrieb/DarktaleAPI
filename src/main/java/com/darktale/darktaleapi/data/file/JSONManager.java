package com.darktale.darktaleapi.data.file;

import java.io.File;
import org.json.JSONObject;

/**
 *
 * @author Ryan
 */
public class JSONManager {

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

    //TODO: Rewrite how we handler json
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
