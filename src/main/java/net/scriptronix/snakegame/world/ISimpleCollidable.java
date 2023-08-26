package net.scriptronix.snakegame.world;

/**
 * Interface for objects that use simple collision (When they occupy the same space)
 */
public interface ISimpleCollidable {
    /**
     * Triggered when another ISimpleColliable collides with this ISimpleCollidable
     * @param sce 
     */
    void onCollision(SimpleCollisionEvent sce);
}
