package com.darktale.darktaleapi.data.file;

import java.io.File;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Ryan
 */
public class JSONManager {

    private static JSONObject getJSONObjectFromTree(JSONFile jsonFile, String... parentObjects) {
        JSONObject currentObject = null;

        int arrLength = parentObjects.length;
        if (!jsonFile.has(parentObjects[arrLength - 1])) {
            jsonFile.put(parentObjects[arrLength - 1], new JSONObject());
        }

        currentObject = jsonFile.getJSONObject(parentObjects[arrLength - 1]);
        //System.out.println("Start: " + parentObjects[arrLength - 1] + ", Length:" + arrLength);

        for (int i = arrLength - 2; i >= 0; i--) {
            // System.out.println("Current: " + parentObjects[i] + "," + i);
            if (!currentObject.has(parentObjects[i])) {
                System.out.println(parentObjects[i + 1] + " doesnt have: " + parentObjects[i]);
                currentObject.put(parentObjects[i], new JSONObject());
            }
            currentObject = currentObject.getJSONObject(parentObjects[i]);
        }

        FileManager.setFileText(jsonFile.getFilePath(), jsonFile.toString());
        // System.out.println("END\n");
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

    public static void removeJSONObject(JSONFile jsonFile, String objectKey, String... parentObjects) {
        if (parentObjects.length <= 0) {
            jsonFile.remove(objectKey);
            FileManager.setFileText(jsonFile.getFilePath(), jsonFile.toString());
            return;
        }

        JSONObject currentObject = getJSONObjectFromTree(jsonFile, parentObjects);
        currentObject.remove(objectKey);
        FileManager.setFileText(jsonFile.getFilePath(), jsonFile.toString());
    }

    public static void appendToJSONArray(JSONFile jsonFile, Object object, String arrayKey, String... parentObjects) {
        if (parentObjects.length <= 0) {
            jsonFile.getJSONArray(arrayKey).put(object);
            FileManager.setFileText(jsonFile.getFilePath(), jsonFile.toString());
            return;
        }

        JSONObject currentObject = getJSONObjectFromTree(jsonFile, parentObjects);

        if (!currentObject.has(arrayKey)) {
            currentObject.put(arrayKey, new JSONArray());
        }

        currentObject.getJSONArray(arrayKey).put(object);
        FileManager.setFileText(jsonFile.getFilePath(), jsonFile.toString());
    }

    public static void removeFromJSONArray(JSONFile jsonFile, Object object, String arrayKey, String... parentObjects) {
        if (parentObjects.length <= 0) {
            JSONArray array = jsonFile.getJSONArray(arrayKey);
            for (int i = 0; i < array.length(); i++) {
                if (array.get(i).equals(object)) {
                    array.remove(i);
                }
            }

            FileManager.setFileText(jsonFile.getFilePath(), jsonFile.toString());
            return;
        }

        JSONObject currentObject = getJSONObjectFromTree(jsonFile, parentObjects);

        if (!currentObject.has(arrayKey)) {
            return;
        }

        JSONArray array = currentObject.getJSONArray(arrayKey);
        for (int i = 0; i < array.length(); i++) {
            if (array.get(i).equals(object)) {
                array.remove(i);
            }
        }

        FileManager.setFileText(jsonFile.getFilePath(), jsonFile.toString());
    }

    public static JSONArray getJSONArray(JSONFile jsonFile, String arrayKey, String... parentObjects) {
        if (parentObjects.length <= 0) {
            if (!jsonFile.has(arrayKey)) {
                jsonFile.put(arrayKey, new JSONArray());
            }
            return jsonFile.getJSONArray(arrayKey);
        }

        JSONObject currentObject = getJSONObjectFromTree(jsonFile, parentObjects);

        if (!currentObject.has(arrayKey)) {
            currentObject.put(arrayKey, new JSONArray());
        }

        return currentObject.getJSONArray(arrayKey);
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

    public static boolean hasObject(JSONFile jsonFile, String objectKey, String... parentObjects) {
        if (parentObjects.length <= 0) {
            if (!jsonFile.has(objectKey)) {
                return false;
            }
            return true;
        }

        if (!jsonFile.has(parentObjects[0])) {
            return false;
        }

        JSONObject currentObject = jsonFile.getJSONObject(parentObjects[0]);

        for (int i = 1; i < parentObjects.length; i++) {
            if (!currentObject.has(parentObjects[i])) {
                return false;
            }
            currentObject = currentObject.getJSONObject(parentObjects[i]);
        }

        return true;
    }
}
