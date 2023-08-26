package net.scriptronix.snakegame.rendering;

/**
 * Interface defining a object which can be rendered by the console renderer.
 */
public interface IConsoleRenderable {

    /**
     * @return An ConsolePixel[] specifying how this element should appear when
     * rendered
     */
    ConsolePixel[] getConsolePixels();
}
