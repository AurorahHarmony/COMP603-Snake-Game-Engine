package net.scriptronix.snakegame.game;

import net.scriptronix.snakegame.world.Scene;
import net.scriptronix.snakegame.world.TextObject;

/**
 * Main menu of the game. This is the first scene that is loaded.
 */
public class MainMenuScene extends Scene {

    public MainMenuScene() {
        this.spawnObject(new MainMenu(this));
    }
    
}
