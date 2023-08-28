package net.scriptronix.snakegame;

import net.scriptronix.snakegame.game.GameScene;
import net.scriptronix.snakegame.input.EInputAction;
import net.scriptronix.snakegame.input.InputManager;
import net.scriptronix.snakegame.message.IMessageHandler;
import net.scriptronix.snakegame.message.Message;
import net.scriptronix.snakegame.message.MessageBus;
import net.scriptronix.snakegame.rendering.ConsoleRenderer;
import net.scriptronix.snakegame.rendering.IRenderer;
import net.scriptronix.snakegame.world.Scene;

/**
 * The main game engine class
 */
public class Engine implements IMessageHandler {
    
    boolean isRunning;
    EngineConfig engineConfig;
    IRenderer renderer;
    Scene scene;

    /**
     * Stands up the engine and starts the game loop.
     */
    public void start() {
        this.isRunning = false;
        this.engineConfig = new EngineConfig();
        this.engineConfig.setVirtualWidth(40);
        this.engineConfig.setVirtualHeight(10);
        this.engineConfig.setTickDuration(300);
        
        this.renderer = new ConsoleRenderer(this.engineConfig);
        this.scene = new GameScene(this);
        
        InputManager.initialize();
        Message.subscribe("INPUT_ACTION", this);
        
        loop();
    }
    
    /**
     * @return the EngineConfig
     */
    public EngineConfig getConfig() {
        return this.engineConfig;
    }

    /**
     * Contains the game loop
     */
    public void loop() {
        isRunning = true;
        while (isRunning) {
            this.update();
            this.render();
            try {
                Thread.sleep(this.engineConfig.getTickDuration());
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
    
    @Override
    public void onMessage(Message msg) {
        if (msg.isCode("INPUT_ACTION") &&
                (EInputAction) msg.getContext() == EInputAction.QUIT)
            System.exit(0);
    }
}
