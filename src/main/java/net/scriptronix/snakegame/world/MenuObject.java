package net.scriptronix.snakegame.world;

import java.util.ArrayList;
import net.scriptronix.snakegame.input.EInputAction;
import net.scriptronix.snakegame.math.Vector2;
import net.scriptronix.snakegame.message.IMessageHandler;
import net.scriptronix.snakegame.message.Message;
import net.scriptronix.snakegame.rendering.ConsolePixel;
import net.scriptronix.snakegame.rendering.IConsoleRenderable;

/**
 * Pre-configured SceneObject for interactive Menus
 */
abstract public class MenuObject extends SceneObject implements IConsoleRenderable, IMessageHandler {

    private int selectedMenuItem = 0;
    private final MenuItem[] menuItems;

    /**
     * Creates a new MenuObject
     *
     * @param scene
     */
    public MenuObject(Scene scene) {
        super(scene);

        Message.subscribe("INPUT_ACTION", this);

        this.menuItems = this.registerMenuItems();
    }

    /**
     * Returns a list of the MenuItems to register in this MenuObject
     *
     * @return
     */
    abstract protected MenuItem[] registerMenuItems();

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

    /**
     * Handle inputs to change the selected menu item
     *
     * @param inputAction
     */
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

    /**
     * Structure allowing for MenuItem. Allows custom callbacks to be provided
     * as the action(), which will be triggered when this MenuItem is selected.
     */
    abstract static public class MenuItem {

        private final String name;

        public MenuItem(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public abstract void action();
        
    }
    
    public static MenuItem createSpacerMenuItem() {
        return new MenuItem("") {
            @Override
            public void action() {
            }
        };
    }
}
