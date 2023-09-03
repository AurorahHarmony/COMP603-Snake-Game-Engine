package net.scriptronix.snakegame.game;

import net.scriptronix.snakegame.Engine;
import net.scriptronix.snakegame.EngineConfig;
import net.scriptronix.snakegame.world.MenuObject;
import net.scriptronix.snakegame.world.Scene;

/**
 *
 * @author minee
 */
public class OptionsMenu extends MenuObject {

    public OptionsMenu(Scene scene) {
        super(scene);
    }

    @Override
    protected MenuObject.MenuItem[] registerMenuItems() {

        return new MenuObject.MenuItem[]{
            // Go back to the Main Menu
            new MenuObject.MenuItem("Main Menu") {
                public void action() {
                    Engine.getInstance().loadScene("net.scriptronix.snakegame.game.MainMenuScene");
                }
            },
            MenuObject.createSpacerMenuItem(),
            // Increase Screen Width
            new MenuObject.MenuItem("Increase Screen Width") {
                public void action() {
                    EngineConfig ec = Engine.getInstance().getConfig();
                    ec.setVirtualWidth(ec.getVirtualWidth() + 1);

                }
            },
            // Decrease Screen Width
            new MenuObject.MenuItem("Decrease Screen Width") {
                public void action() {
                    EngineConfig ec = Engine.getInstance().getConfig();
                    ec.setVirtualWidth(ec.getVirtualWidth() + 1);
                }
            }, // TODO: Add more options
        };
    }

}
