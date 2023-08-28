package net.scriptronix.snakegame;

/**
 * A wrapper for engine configuration
 */
public class EngineConfig {

    /**
     * The width of the view port in engine units
     */
    private int virtualWidth = 0;
    /**
     * The height of the view port in engine units
     */
    private int virtualHeight = 0;
    
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
        this.virtualWidth = virtualWidth;
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
    }

    /**
     * @return the tickDuration
     */
    public int getTickDuration() {
        return tickDuration;
    }

    /**
     * Sets the duration of a single tick in milliseconds. 
     * A value of 1000 would make the engine run at 1fps
     * @param tickDuration the tickDuration to set in milliseconds
     */
    public void setTickDuration(int tickDuration) {
        this.tickDuration = tickDuration;
    }

}
