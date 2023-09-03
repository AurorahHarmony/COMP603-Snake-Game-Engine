package net.scriptronix.snakegame.assets;

import java.util.HashMap;
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
