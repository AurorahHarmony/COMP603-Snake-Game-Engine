package net.scriptronix.snakegame.world;

import net.scriptronix.snakegame.input.EInputAction;
import net.scriptronix.snakegame.math.Vector2;
import net.scriptronix.snakegame.message.IMessageHandler;
import net.scriptronix.snakegame.message.Message;
import net.scriptronix.snakegame.rendering.ConsolePixel;
import net.scriptronix.snakegame.rendering.IConsoleRenderable;

/**
 * Stores the information about an object in the scene.
 */
public class SceneObject implements IConsoleRenderable, IMessageHandler {

    Vector2 velocity = Vector2.zero();
    Vector2 position = Vector2.zero();

    public SceneObject() {
        this.init();
    }

    private void init() {
        Message.subscribe("INPUT_ACTION", this);
    }

    @Override
    public ConsolePixel[] getConsolePixels() {
        ConsolePixel cPixel = new ConsolePixel(position, 'o');
        ConsolePixel[] cPixArr = {cPixel};
        return cPixArr;
    }

    @Override
    public void onMessage(Message msg) {
        if (msg.isCode("INPUT_ACTION")) {
            switch ((EInputAction) msg.getContext()) {
                case MOVE_LEFT:
                    velocity.setX(-1);
                    velocity.setY(0);
                    break;
                case MOVE_UP:
                    velocity.setX(0);
                    velocity.setY(-1);
                    break;
                case MOVE_RIGHT:
                    velocity.setX(1);
                    velocity.setY(0);
                    break;
                case MOVE_DOWN:
                    velocity.setX(0);
                    velocity.setY(1);
                    break;
                default:
                    break;

            }

        }
    }

    void update() {
        position.add(velocity);
    }

}
