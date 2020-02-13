package com.darktale.darktaleapi.data.file;

import java.io.File;
import org.json.JSONObject;

/**
 *
 * @author Ryan
 */
public class JSONManager {

    private static JSONObject getJSONObjectFromTree(JSONFile jsonFile, String... parentObjects) {
        JSONObject currentObject = null;

        if (!jsonFile.has(parentObjects[0])) {
            jsonFile.put(parentObjects[0], new JSONObject());
        }

        currentObject = jsonFile.getJSONObject(parentObjects[0]);

        for (int i = 1; i < parentObjects.length; i++) {
            if (!jsonFile.has(parentObjects[i])) {
                jsonFile.put(parentObjects[0], new JSONObject());
            }
            currentObject = currentObject.getJSONObject(parentObjects[i]);
        }

        return currentObject;
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

    public static void appendJSONObject(JSONFile jsonFile, Object object, String objectKey, String... parentObjects) {
        if (parentObjects.length <= 0) {
            jsonFile.put(objectKey, object);
            FileManager.setFileText(jsonFile.getFilePath(), jsonFile.toString());
            return;
        }

        JSONObject currentObject = getJSONObjectFromTree(jsonFile, parentObjects);

        currentObject.put(objectKey, object.toString());
        FileManager.setFileText(jsonFile.getFilePath(), jsonFile.toString());
    }

    public static Object getObject(JSONFile jsonFile, String objectKey, String... parentObjects) {
        if (parentObjects.length <= 0) {
            if (!jsonFile.has(objectKey)) {
                jsonFile.put(objectKey, new JSONObject());
            }
            return jsonFile.get(objectKey);
        }

        JSONObject currentObject = getJSONObjectFromTree(jsonFile, parentObjects);

        if (!currentObject.has(objectKey)) {
            return null;
        }

        return currentObject.get(objectKey);
    }
}
