package net.scriptronix.snakegame.world;

import net.scriptronix.snakegame.Engine;
import net.scriptronix.snakegame.EngineConfig;
import net.scriptronix.snakegame.math.Vector2;

/**
 * an object that can be placed in the scene
 */
public abstract class SceneObject {

    protected Vector2 position;
    protected final Scene scene;

    /**
     * Creates a new SceneObject at (0,0)
     *
     * @param scene A reference to the scene
     */
    public SceneObject(Scene scene) {
        this(scene, Vector2.zero());
    }

    /**
     * Creates a new SceneObject at the provided position
     *
     * @param scene A reference to the scene
     * @param initPosition The starting position for the SceneObject
     */
    public SceneObject(Scene scene, Vector2 initPosition) {
        this.scene = scene;
        this.position = initPosition;
    }

    /**
     * @return The Vector2 position of this object
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Updates the state of this object. It should be called on every tick.
     */
    public void update() {
    }

    protected EngineConfig getEngineConfig() {
        return Engine.getInstance().getConfig();
    }

    /**
     * @return Returns true if this SceneObject is off screen
     */
    protected boolean isOffScreen() {
        int virtualWidth = getEngineConfig().getVirtualWidth();
        int virtualHeight = getEngineConfig().getVirtualHeight();
        int x = position.getX();
        int y = position.getY();

        return x < 0 || x > virtualWidth - 1 || y < 0 || y > virtualHeight - 1;
    }
    
    /**
     * Cleans up this object.
     */
    abstract public void destroy();
}
