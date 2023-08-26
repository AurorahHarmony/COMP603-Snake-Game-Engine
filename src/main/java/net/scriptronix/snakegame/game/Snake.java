package net.scriptronix.snakegame.game;

import net.scriptronix.snakegame.EngineConfig;
import net.scriptronix.snakegame.input.EInputAction;
import net.scriptronix.snakegame.math.Vector2;
import net.scriptronix.snakegame.message.IMessageHandler;
import net.scriptronix.snakegame.message.Message;
import net.scriptronix.snakegame.rendering.ConsolePixel;
import net.scriptronix.snakegame.rendering.IConsoleRenderable;
import net.scriptronix.snakegame.world.ISimpleCollidable;
import net.scriptronix.snakegame.world.Scene;
import net.scriptronix.snakegame.world.SceneObject;
import net.scriptronix.snakegame.world.SimpleCollisionEvent;

/**
 * The snake player class
 */
public class Snake extends SceneObject implements IConsoleRenderable, IMessageHandler, ISimpleCollidable {

    Vector2 velocity = new Vector2(0, -1);
    Integer size = 0;

    public Snake(Scene scene) {
        super(scene);
        this.init();
    }

    private void init() {
        EngineConfig engineConfig = scene.getEngineInstance().getConfig();
        int halfEngineWidth = engineConfig.getVirtualWidth() / 2;
        int halfEngineHeight = engineConfig.getVirtualHeight() / 2;
        this.position = new Vector2(halfEngineWidth, halfEngineHeight);
        
        Message.subscribe("INPUT_ACTION", this);
    }

    @Override
    public ConsolePixel[] getConsolePixels() {
        ConsolePixel cPixel = new ConsolePixel(position, size.toString().charAt(0));
        ConsolePixel[] cPixArr = {cPixel};
        return cPixArr;
    }

    @Override
    public void onMessage(Message msg) {
        if (msg.isCode("INPUT_ACTION"))
            handleInput((EInputAction) msg.getContext());
    }

    private void handleInput(EInputAction inputAction) {
        switch (inputAction) {
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

    @Override
    public void update() {
        position.add(velocity);
    }

    @Override
    public void onCollision(SimpleCollisionEvent sce) {
        if (sce.otherCollidable() instanceof Food)
            size++;
    }
}
