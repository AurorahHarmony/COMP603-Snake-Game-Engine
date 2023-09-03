package net.scriptronix.snakegame.game;

import net.scriptronix.snakegame.Engine;
import net.scriptronix.snakegame.world.MenuObject;
import net.scriptronix.snakegame.world.Scene;

/**
 * Represents the interactive Main Menu
 */
public class MainMenu extends MenuObject {

    public MainMenu(Scene scene) {
        super(scene);
    }

    @Override
    protected MenuObject.MenuItem[] registerMenuItems() {
        return new MenuObject.MenuItem[]{
            // Start the game
            new MenuItem("Start") {
                public void action() {
                    Engine.getInstance().loadScene("net.scriptronix.snakegame.game.GameScene");
                }
            },
            // Quit the game
            new MenuObject.MenuItem("Quit") {
                public void action() {
                    System.exit(0);
                }
            }
        };
    }

}
