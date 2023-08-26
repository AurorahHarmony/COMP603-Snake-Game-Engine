package net.scriptronix.snakegame.world;

/**
 * Stores the information about a simple collision
 */
public class SimpleCollisionEvent {
    final private ISimpleCollidable collidable;

    /**
     * Creates a new SimpleCollisionEvent
     * @param collidable the object that generated the collision
     */
    public SimpleCollisionEvent(ISimpleCollidable collidable) {
        this.collidable = collidable;
    }

    /**
     * @return the collidable that generated the collision
     */
    public ISimpleCollidable collidable() {
        return collidable;
    }
}
