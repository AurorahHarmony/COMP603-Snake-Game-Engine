package net.scriptronix.snakegame;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.scriptronix.snakegame.game.GameScene;
import net.scriptronix.snakegame.input.EInputAction;
import net.scriptronix.snakegame.input.InputManager;
import net.scriptronix.snakegame.message.IMessageHandler;
import net.scriptronix.snakegame.message.Message;
import net.scriptronix.snakegame.message.MessageBus;
import net.scriptronix.snakegame.rendering.ConsoleRenderer;
import net.scriptronix.snakegame.rendering.IRenderer;
import net.scriptronix.snakegame.world.Scene;
import net.scriptronix.snakegame.world.SceneFactory;

/**
 * The main game engine class
 */
public class Engine implements IMessageHandler {

    private static Engine engineInstance;

    boolean isRunning;
    EngineConfig engineConfig;
    IRenderer renderer;
    Scene scene;

    /**
     * Stands up the engine and starts the game loop.
     */
    public static void start() {
        engineInstance = new Engine();
        engineInstance.init();

    }

    private Engine() { // Singleton

    }

    private void init() {
        this.isRunning = false;
        this.engineConfig = new EngineConfig();
        this.engineConfig.setVirtualWidth(40);
        this.engineConfig.setVirtualHeight(10);
        this.engineConfig.setTickDuration(300);

        this.renderer = new ConsoleRenderer(this.engineConfig);

        this.loadScene("net.scriptronix.snakegame.game.MainMenuScene");
//        loadScene("net.scriptronix.snakegame.game.GameScene");

//        this.scene = new GameScene(this);
        InputManager.initialize();
        Message.subscribe("INPUT_ACTION", this);

        loop();
    }

    public static Engine getInstance() {
        return engineInstance;
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

    /**
     * Load a new scene based on its fully qualified name
     *
     * @param sceneClassName
     */
    public void loadScene(String sceneClassName) {
        Scene newScene;
        try {
            newScene = SceneFactory.createScene(sceneClassName, this);
            this.scene = null;
            this.scene = newScene;
        } catch (ClassNotFoundException | IllegalArgumentException | InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
            // An invalid class name was provided. This is a fatal exception.
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }
}
