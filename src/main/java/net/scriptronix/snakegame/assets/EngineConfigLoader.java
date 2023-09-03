package net.scriptronix.snakegame.assets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
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

}
