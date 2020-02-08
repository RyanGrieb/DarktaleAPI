package com.darktale.darktaleapi.data.file;

import org.json.JSONObject;

/**
 *
 * @author Ryan
 */
public class JSONFile extends JSONObject {

    private final String jsonFilePath;

    public JSONFile(String jsonPath) {
        super(FileManager.readFile(jsonPath));

        this.jsonFilePath = jsonPath;
    }

    public String getFilePath() {
        return this.jsonFilePath;
    }
}
