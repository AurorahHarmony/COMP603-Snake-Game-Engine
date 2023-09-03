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
import net.scriptronix.snakegame.EngineConfig;

/**
 * Loads Engine configuration
 */
public class EngineConfigLoader {

    public static EngineConfig load(String configPath) {
        BufferedReader br = null;
        HashMap<String, String> options = new HashMap<>();

        try {
            br = new BufferedReader(new FileReader(configPath));

            String line = null;
            while ((line = br.readLine()) != null) {
                StringTokenizer tokens = new StringTokenizer(line, "=");
                if (tokens.countTokens() != 2)
                    continue; // Invalid length. Try next line
                options.put(tokens.nextToken(), tokens.nextToken());
            }
        } catch (IOException ex) {
            Logger.getLogger(EngineConfigLoader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null)
                try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(EngineConfigLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return EngineConfig.fromHashMap(options);
    }

    /**
     * Save an EngineConfig to the specified path
     *
     * @param engineConfig
     * @param savePath
     */
    public static void save(EngineConfig engineConfig, String savePath) {
        HashMap<String, String> ec = engineConfig.toHashMap();

        StringBuilder saveFilebuffer = new StringBuilder();

        for (Map.Entry<String, String> option : ec.entrySet()) {
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
        } catch (Exception e) {
            // Couldn't create a save file
        }
    }

}
