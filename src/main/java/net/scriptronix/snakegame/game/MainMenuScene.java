package net.scriptronix.snakegame.game;

import net.scriptronix.snakegame.Engine;
import net.scriptronix.snakegame.world.Scene;

/**
 * Main menu of the game. This is the first scene that is loaded.
 */
public class MainMenuScene extends Scene {

    public MainMenuScene(Engine engine) {
        super(engine);
        
        this.spawnObject(new MainMenu(this));
        
    }
    
}
