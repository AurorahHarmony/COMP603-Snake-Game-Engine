package net.scriptronix.snakegame.game;

import net.scriptronix.snakegame.Engine;
import net.scriptronix.snakegame.world.MenuObject;
import net.scriptronix.snakegame.world.Scene;

/**
 * Represents the interactive Main Menu
 */
public class MainMenuButton extends MenuObject {

    public MainMenuButton(Scene scene) {
        super(scene);
    }

    @Override
    protected MenuObject.MenuItem[] registerMenuItems() {
        return new MenuObject.MenuItem[]{
            // Main Menu return button
            new MenuItem("Press ENTER to continue") {
                public void action() {
                    Engine.getInstance().loadScene("net.scriptronix.snakegame.game.MainMenuScene");
                }
            },
        };
    }

}
