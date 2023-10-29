package net.scriptronix.snakegame.assets;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Loads files with Key=Value pairs into a HashMap<String, String>
 *
 * @author minee
 */
public class KeyValueFileLoader {

    /**
     * Loads a Key=Value file into a HashMap<String, String>
     *
     * @param filePath
     * @return
     */
    public static HashMap<String, String> loadFile(String filePath) {
        BufferedReader br = null;
        HashMap<String, String> map = new HashMap<>();
        try {
            br = new BufferedReader(new FileReader(filePath));

            String line = null;
            while ((line = br.readLine()) != null) {
                StringTokenizer tokens = new StringTokenizer(line, "=");
                if (tokens.countTokens() != 2)
                    continue; // Invalid length. Try next line
                map.put(tokens.nextToken(), tokens.nextToken());
            }
        } catch (IOException ex) {
            System.out.println("File " + filePath + " not found. Moving on...");
        } finally {
            if (br != null)
                try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(KeyValueFileLoader.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return map;
    }

    public static void saveFile(HashMap<String, String> map, String savePath) {
        StringBuilder saveFilebuffer = new StringBuilder();

        for (Map.Entry<String, String> option : map.entrySet()) {
            String optionName = option.getKey();
            String optionValue = option.getValue();
            saveFilebuffer
                    .append(optionName)
                    .append("=")
                    .append(optionValue)
                    .append('\n');
        }

        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(savePath);
            fileOut.write(saveFilebuffer.toString().getBytes());
            fileOut.close();
        } catch (Exception ex) {
            // Couldn't create a save file
            Logger.getLogger(KeyValueFileLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
