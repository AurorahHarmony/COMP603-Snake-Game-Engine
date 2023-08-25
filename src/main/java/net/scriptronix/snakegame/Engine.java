package net.scriptronix.snakegame;

import net.scriptronix.snakegame.game.GameState;
import net.scriptronix.snakegame.input.InputManager;
import net.scriptronix.snakegame.message.MessageBus;
import net.scriptronix.snakegame.rendering.ConsoleRenderer;
import net.scriptronix.snakegame.rendering.IRenderer;
import net.scriptronix.snakegame.world.Scene;

/**
 * The main game engine class
 */
public class Engine {

    boolean isRunning;
    boolean isTicking;
    IRenderer renderer;
    Scene scene;

    /**
     * Stands up the engine and starts the game loop.
     */
    public void start() {
        this.isRunning = false;
        this.renderer = new ConsoleRenderer();
        this.scene = new Scene();

        InputManager.initialize();

        loop();
    }

    /**
     * Contains the game loop
     */
    public void loop() {
        isRunning = true;
        while (isRunning) {
            this.update();
            render();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                isRunning = false;
                e.printStackTrace();
            }
        }
    }

    private void update() {
        // TODO: Implement delta time for Assessment 2
        MessageBus.update();
        this.scene.update();
    }

    private void render() {
        renderer.render(this.scene);
    }
}
