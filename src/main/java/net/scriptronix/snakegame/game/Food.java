package net.scriptronix.snakegame.game;

import net.scriptronix.snakegame.math.Vector2;
import net.scriptronix.snakegame.rendering.ConsolePixel;
import net.scriptronix.snakegame.rendering.IConsoleRenderable;
import net.scriptronix.snakegame.world.ISimpleCollidable;
import net.scriptronix.snakegame.world.SceneObject;
import net.scriptronix.snakegame.world.SimpleCollisionEvent;

/**
 * Represents a piece of food that the snake can eat
 */
public class Food extends SceneObject implements IConsoleRenderable, ISimpleCollidable {
    
    char symbol = '@';

    public Food() {
        this.position = new Vector2(4,4);
    }

    
    @Override
    public ConsolePixel[] getConsolePixels() {
        return new ConsolePixel[]{new ConsolePixel(position, symbol)};
    }

    @Override
    public void onCollision(SimpleCollisionEvent sce) {
        if (sce.collidable() instanceof Snake) {
            this.symbol = 'e';
        }
    }

}
