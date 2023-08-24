package net.scriptronix.snakegame.world;

import net.scriptronix.snakegame.math.Vector2;
import net.scriptronix.snakegame.rendering.ConsolePixel;
import net.scriptronix.snakegame.rendering.IConsoleRenderable;

/**
 * Stores the information about an object in the scene.
 */
public class SceneObject implements IConsoleRenderable{

    @Override
    public ConsolePixel[] getConsolePixels() {
        ConsolePixel cPixel = new ConsolePixel(Vector2.zero(), 'x');
        ConsolePixel[] cPixArr = {cPixel};
        return cPixArr;
    }
    
}
