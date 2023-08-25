package net.scriptronix.snakegame.world;

import net.scriptronix.snakegame.math.Vector2;

/**
 * an object that can be placed in the scene
 */
public abstract class SceneObject {

    protected Vector2 position;

    public SceneObject() {
        this(Vector2.zero());
    }

    public SceneObject(Vector2 position) {
        this.position = position;
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
;
}
