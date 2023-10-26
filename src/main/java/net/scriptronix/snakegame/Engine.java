package net.scriptronix.snakegame;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.scriptronix.snakegame.assets.EngineConfigLoader;
import net.scriptronix.snakegame.input.EInputAction;
import net.scriptronix.snakegame.input.InputManager;
import net.scriptronix.snakegame.message.IMessageHandler;
import net.scriptronix.snakegame.message.Message;
import net.scriptronix.snakegame.message.MessageBus;
import net.scriptronix.snakegame.rendering.IRenderer;
import net.scriptronix.snakegame.rendering.SwingRenderer;
import net.scriptronix.snakegame.world.Scene;
import net.scriptronix.snakegame.world.SceneFactory;

/**
 * The main game engine class
 */
public class Engine implements IMessageHandler {
    
    public static final String ENGINE_CONFIG = "./engine.weconf";

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

        this.engineConfig = EngineConfigLoader.load(ENGINE_CONFIG);
        this.renderer = new SwingRenderer(this.engineConfig);

        InputManager.initialize();
        Message.subscribe("INPUT_ACTION", this);
        
        this.loadScene("net.scriptronix.snakegame.game.MainMenuScene");

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
     * 
     */
    public void saveConfig() {
        EngineConfigLoader.save(this.engineConfig, ENGINE_CONFIG);
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
            newScene = SceneFactory.createScene(sceneClassName);
            if (this.scene != null) {
                this.scene.destroy();
                this.scene = null;
            }
            this.scene = newScene;
        } catch (ClassNotFoundException | IllegalArgumentException | InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
            // An invalid class name was provided. This is a fatal exception.
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }
}
