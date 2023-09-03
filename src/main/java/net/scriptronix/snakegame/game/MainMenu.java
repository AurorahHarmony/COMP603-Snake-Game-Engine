package net.scriptronix.snakegame.game;

import java.awt.MenuItem;
import java.util.ArrayList;
import net.scriptronix.snakegame.input.EInputAction;
import net.scriptronix.snakegame.math.Vector2;
import net.scriptronix.snakegame.message.IMessageHandler;
import net.scriptronix.snakegame.message.Message;
import net.scriptronix.snakegame.rendering.ConsolePixel;
import net.scriptronix.snakegame.rendering.IConsoleRenderable;
import net.scriptronix.snakegame.world.Scene;
import net.scriptronix.snakegame.world.SceneObject;

/**
 * Represents the interactive Main Menu
 */
public class MainMenu extends SceneObject implements IConsoleRenderable, IMessageHandler {

    private int selectedMenuItem = 0;
    private MenuItem[] menuItems;

    public MainMenu(Scene scene) {
        super(scene);

        Message.subscribe("INPUT_ACTION", this);

        this.menuItems = new MenuItem[]{
            new MenuItem("Start") {
                public void action() {
//                    Engine.loadScene("net.scriptronix.snakegame.game.GameScene");
                }
            },
            new MenuItem("Quit") {
                public void action() {
                    System.exit(0);
                }
            }
        };
    }

    @Override
    public ConsolePixel[] getConsolePixels() {
        ArrayList<ConsolePixel> pixels = new ArrayList<>();

        // Cursor
        Vector2 pixelPosition = Vector2.newFrom(this.position);
        pixelPosition.add(0, this.selectedMenuItem);
        pixels.add(new ConsolePixel(pixelPosition, '>'));

        // MenuItems                
        for (int i = 0; i < this.menuItems.length; i++) {
            char[] chars = this.menuItems[i].getName().toCharArray();
            for (int j = 0; j < chars.length; j++) {
                pixelPosition = Vector2.newFrom(this.position);
                pixelPosition.add(1 + j, i); // Offset one space right to allow space for the cursor
                pixels.add(new ConsolePixel(pixelPosition, chars[j]));
            }
        }

        return pixels.toArray(ConsolePixel[]::new);
    }

    @Override
    public void onMessage(Message msg) {
        if (msg.isCode("INPUT_ACTION"))
            handleInput((EInputAction) msg.getContext());
    }

    private void handleInput(EInputAction inputAction) {
        switch (inputAction) {
            case MOVE_UP:
                this.decrementSelected();
                break;
            case MOVE_DOWN:
                this.incrementSelected();
                break;
            case SELECT:
                this.menuItems[selectedMenuItem].action();
            default:
        }
    }

    /**
     * Move the selection cursor down one
     */
    private void incrementSelected() {
        if (this.selectedMenuItem >= this.menuItems.length - 1) {
            this.selectedMenuItem = 0;
            return;
        }
        this.selectedMenuItem++;
    }

    /**
     * Move the selection cursor up one
     */
    private void decrementSelected() {
        if (this.selectedMenuItem <= 0) {
            this.selectedMenuItem = this.menuItems.length - 1;
            return;
        }
        this.selectedMenuItem--;
    }

    abstract class MenuItem {

        private String name;

        public MenuItem(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        abstract void action();
    }
}
