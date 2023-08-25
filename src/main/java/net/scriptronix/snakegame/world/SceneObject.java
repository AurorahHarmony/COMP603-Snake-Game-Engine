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

    char symbol = 'x';

    public SceneObject() {
        this.init();
    }

    private void init() {
        Message.subscribe("INPUT_ACTION", this);
    }

    @Override
    public ConsolePixel[] getConsolePixels() {
        ConsolePixel cPixel = new ConsolePixel(Vector2.zero(), symbol);
        ConsolePixel[] cPixArr = {cPixel};
        return cPixArr;
    }

    @Override
    public void onMessage(Message msg) {
        if (msg.isCode("INPUT_ACTION")) {
            switch ((EInputAction) msg.getContext()) {
                case MOVE_LEFT:
                    symbol = 'l';
                    break;
                case MOVE_UP:
                    symbol = 'u';
                    break;
                case MOVE_RIGHT:
                    symbol = 'r';
                    break;
                case MOVE_DOWN:
                    symbol = 'd';
                    break;
                default:
                    break;

            }

        }
    }

}
