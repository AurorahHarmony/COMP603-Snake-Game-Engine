package net.scriptronix.snakegame.world;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import net.scriptronix.snakegame.input.EInputAction;
import net.scriptronix.snakegame.message.IMessageHandler;
import net.scriptronix.snakegame.message.Message;
import net.scriptronix.snakegame.rendering.ISwingRenderable;
import net.scriptronix.snakegame.rendering.SwingRenderer;

/**
 * Pre-configured SceneObject for interactive Menus
 */
abstract public class MenuObject extends SceneObject implements ISwingRenderable, IMessageHandler {

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
    public void draw(Graphics g, ImageObserver observer, int scaleFactor) {
        // Cursor
        g.drawString(">", this.position.getX() * scaleFactor, (this.position.getY() * scaleFactor) + (this.selectedMenuItem * scaleFactor) + scaleFactor);

        // MenuItems
        for (int i = 0; i < this.menuItems.length; i++) {
            MenuItem menuItem = this.menuItems[i];
            g.drawString(menuItem.getName(), (this.position.getX() * scaleFactor) + scaleFactor, (this.position.getY() * scaleFactor) + (i * scaleFactor) + scaleFactor);
        }

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

    @Override
    public void destroy() {
        Message.unsubscribe("INPUT_ACTION", this);
    }

}
