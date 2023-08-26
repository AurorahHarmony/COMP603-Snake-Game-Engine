package net.scriptronix.snakegame.world;

/**
 * Interface for objects that use simple collision (When they occupy the same space)
 */
public interface ISimpleCollidable {
    void onCollision(SimpleCollisionEvent sce);
}
