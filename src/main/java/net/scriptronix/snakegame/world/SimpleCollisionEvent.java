package net.scriptronix.snakegame.world;

/**
 * Stores the information about a simple collision
 */
public class SimpleCollisionEvent {
    final private ISimpleCollidable otherCollidable;

    /**
     * Creates a new SimpleCollisionEvent
     * @param collidable the object that generated the collision
     */
    public SimpleCollisionEvent(ISimpleCollidable collidable) {
        this.otherCollidable = collidable;
    }

    /**
     * @return the ISimpleCollidable that generated the collision
     */
    public ISimpleCollidable otherCollidable() {
        return otherCollidable;
    }
}
