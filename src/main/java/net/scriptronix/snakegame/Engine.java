package net.scriptronix.snakegame;

import net.scriptronix.snakegame.game.GameState;
import net.scriptronix.snakegame.rendering.ConsoleRenderer;
import net.scriptronix.snakegame.rendering.IRenderer;

/**
 * The main game engine class
 */
public class Engine {

    boolean isRunning;
    boolean isTicking;
    IRenderer renderer;
    GameState gameState;

    /**
     * Stands up the engine and starts the game loop.
     */
    public void start() {
        this.isRunning = false;
        this.renderer = new ConsoleRenderer();
        this.gameState = new GameState();
        
        loop();
    }

    /**
     * Contains the game loop
     */
    public void loop() {
        isRunning = true;
        while (isRunning) {
            render();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                isRunning = false;
                e.printStackTrace();
            }
        }
    }

    private void render() {
        renderer.render(gameState);
    }
}
