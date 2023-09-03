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
        HashMap<String, String> optionsMap = KeyValueFileLoader.loadFile(configPath);

        return EngineConfig.fromHashMap(optionsMap);
    }

    /**
     * Save an EngineConfig to the specified path
     *
     * @param engineConfig
     * @param savePath
     */
    public static void save(EngineConfig engineConfig, String savePath) {
        HashMap<String, String> ec = engineConfig.toHashMap();

        KeyValueFileLoader.saveFile(ec, savePath);
    }

}
