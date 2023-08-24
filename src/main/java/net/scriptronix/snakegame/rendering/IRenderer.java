package net.scriptronix.snakegame.rendering;

import net.scriptronix.snakegame.world.Scene;

/**
 * Interface defining a renderer
 */
public interface IRenderer {
    /**
     * Renders the game in its current state.
     * @param scene the current state of the game
     */
    public void render(Scene scene);
}
