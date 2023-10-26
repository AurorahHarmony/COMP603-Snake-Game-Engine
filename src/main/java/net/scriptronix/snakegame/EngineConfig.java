package net.scriptronix.snakegame;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import net.scriptronix.snakegame.message.Message;

/**
 * A wrapper for engine configuration
 */
public class EngineConfig {

    /**
     * The width of the view port in engine units
     */
    private int virtualWidth = 40;
    /**
     * The height of the view port in engine units
     */
    private int virtualHeight = 40;

    /**
     * The duration between each tick in milliseconds
     */
    private int tickDuration = 500;

    /**
     * @return the virtualWidth
     */
    public int getVirtualWidth() {
        return virtualWidth;
    }

    /**
     * @param virtualWidth the virtualWidth to set
     */
    public void setVirtualWidth(int virtualWidth) {
        this.virtualWidth = Math.max(virtualWidth, 20); // Set a min of 20
        Message.send("SCREEN_RESIZED", this);
    }

    /**
     * @return the virtualHeight
     */
    public int getVirtualHeight() {
        return virtualHeight;
    }

    /**
     * @param virtualHeight the virtualHeight to set
     */
    public void setVirtualHeight(int virtualHeight) {
        this.virtualHeight = virtualHeight;
        Message.send("SCREEN_RESIZED", this);
    }

    /**
     * @return the tickDuration
     */
    public int getTickDuration() {
        return tickDuration;
    }

    /**
     * Sets the duration of a single tick in milliseconds. A value of 1000 would
     * make the engine run at 1fps
     *
     * @param tickDuration the tickDuration to set in milliseconds
     */
    public void setTickDuration(int tickDuration) {
        this.tickDuration = tickDuration;
    }
    
    /**
     * @return EngineConfig in HashMap form
     */
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> ec = new HashMap<>();
        
        ec.put("VirtualWidth", Integer.toString(this.virtualWidth));
        ec.put("virtualHeight", Integer.toString(this.virtualHeight));
        ec.put("tickDuration", Integer.toString(this.tickDuration));
                
        return ec;
    }
    
    /**
     * Generates an EngineConfig from a HashMap
     *
     * @param options
     * @return
     */
    public static EngineConfig fromHashMap(HashMap<String, String> options) {
        EngineConfig ec = new EngineConfig();

        for (Map.Entry<String, String> entry : options.entrySet()) {
            String key = entry.getKey();
            String methodName = "set" + key;

            try {
                // TODO: Add support for string values
                Method method = ec.getClass().getMethod(methodName, int.class);

                method.invoke(ec, Integer.parseInt(entry.getValue()));
            } catch (Exception e) {
                // We don't care if this fails. It just means that the option doesn't exist.
            }
        }

        return ec;
    }
    
}
