package net.scriptronix.snakegame;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import net.scriptronix.snakegame.message.Message;

/**
 * A wrapper for engine configuration
 */
public class EngineConfig {

    final private int MINSCREENWIDTH = 20;
    final private int MAXSCREENWIDTH = 40;
    final private int MINSCREENHEIGHT = 20;
    final private int MAXSCREENHEIGHT = 40;

    /**
     * The width of the view port in engine units
     */
    private int virtualWidth = MINSCREENWIDTH;
    /**
     * The height of the view port in engine units
     */
    private int virtualHeight = MINSCREENWIDTH;

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
        this.virtualWidth = Math.min(Math.max(virtualWidth, MINSCREENWIDTH), MAXSCREENWIDTH);
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
        this.virtualHeight = Math.min(Math.max(virtualHeight, MINSCREENHEIGHT), MAXSCREENHEIGHT);
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
     * Updates the TickDuratin to match the fps
     *
     * @param fps
     */
    public void setFPS(int fps) {
        this.tickDuration = 1000 / fps;
    }

    /**
     * @return EngineConfig in HashMap form
     */
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> ec = new HashMap<>();

        ec.put("VirtualWidth", Integer.toString(this.virtualWidth));
        ec.put("VirtualHeight", Integer.toString(this.virtualHeight));

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

    /**
     * Shortcut to call saveConfig on the Engine Instance
     */
    public void save() {
        Engine.getInstance().saveConfig();
    }

}
