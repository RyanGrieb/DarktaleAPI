package com.darktale.darktaleapi.data.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 *
 * @author Ryan
 */
public class FileManager {

    public static String readFile(String filename) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void setFileText(String path, String text) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            bw.append(text);
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void makeDirectory(String directory) {
        File resourceDir = new File(directory);

        if (!resourceDir.isDirectory()) {
            try {
                resourceDir.mkdir();
            } catch (Exception e) {
            }
        }
    }

    public static void makeFile(String filePath) {
        File file = new File(filePath);

        if (!file.isFile()) {
            try {
                file.createNewFile();
                //FileManager.setFileText(filePath, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Append to file:
    /*

        if (newCommandsFile) {
            try (FileWriter fw = new FileWriter(commandsPath, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw)) {
                out.println(" " + event.getCommand().getName() + ":");
                out.println("  description: " + event.getCommand().getDescription());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

     */
}
