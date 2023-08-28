package net.scriptronix.snakegame.game;

import java.util.ArrayList;
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

    final private ArrayList<Vector2> bodyParts = new ArrayList<>(); // Head is always the position. Body follows the head.
    private EMovementDirection direction = EMovementDirection.UP;
    private EMovementDirection lastMovedDirection = direction;
    private Vector2 tailLastPosition;

    public Snake(Scene scene) {
        super(scene);
        this.init();
    }

    private void init() {
        int halfEngineWidth = getEngineConfig().getVirtualWidth() / 2;
        int halfEngineHeight = getEngineConfig().getVirtualHeight() / 2;
        this.position = new Vector2(halfEngineWidth, halfEngineHeight);

        Message.subscribe("INPUT_ACTION", this);

        // Add some starting parts to the snake
        this.bodyParts.add(new Vector2(this.position.getX(), this.position.getY() + 1));
        this.bodyParts.add(new Vector2(this.position.getX(), this.position.getY() + 2));

        this.tailLastPosition = Vector2.newFrom(this.bodyParts.get(this.bodyParts.size() - 1));
    }

    @Override
    public ConsolePixel[] getConsolePixels() {
        ConsolePixel[] cPixArr = new ConsolePixel[bodyParts.size() + 1];

        ConsolePixel headCPixel = new ConsolePixel(position, 'X');
        cPixArr[0] = headCPixel;
        for (int i = 0; i < bodyParts.size(); i++) {
            cPixArr[i + 1] = new ConsolePixel(bodyParts.get(i), 'O');
        }

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
                if (this.lastMovedDirection != EMovementDirection.RIGHT)
                    this.direction = EMovementDirection.LEFT;
                break;
            case MOVE_UP:
                if (this.lastMovedDirection != EMovementDirection.DOWN)
                    this.direction = EMovementDirection.UP;
                break;
            case MOVE_RIGHT:
                if (this.lastMovedDirection != EMovementDirection.LEFT)
                    this.direction = EMovementDirection.RIGHT;
                break;
            case MOVE_DOWN:
                if (this.lastMovedDirection != EMovementDirection.UP)
                    this.direction = EMovementDirection.DOWN;
                break;
            default:
                break;
        }
    }

    @Override
    public void update() {
        Vector2 lastPosition = Vector2.newFrom(this.position);
        
        position.add(this.getVelocity());

        if (this.isOffScreen())
            System.exit(0);

        Vector2 currentPosition = Vector2.zero();
        for (Vector2 bodyPart : this.bodyParts) {
            currentPosition.copyFrom(bodyPart);
            bodyPart.copyFrom(lastPosition);
            lastPosition.copyFrom(currentPosition);
        }

        tailLastPosition.copyFrom(lastPosition);
        
        if (hasCrashedIntoBody())
            System.exit(0);
        
        this.lastMovedDirection = this.direction;

    }
    
    /**
     * @return the current direction into a velocity Vector2
     */
    private Vector2 getVelocity() {
        Vector2 velocity = new Vector2(0, -1); // Upwards by default
    switch (this.direction) {
            case LEFT:
                velocity.setX(-1);
                velocity.setY(0);
                break;
            case UP:
                velocity.setX(0);
                velocity.setY(-1);
                break;
            case RIGHT:
                velocity.setX(1);
                velocity.setY(0);
                break;
            case DOWN:
                velocity.setX(0);
                velocity.setY(1);
                break;
            default:
                break;
        }
    return velocity;
    }

    /**
     * Checks to see if the head of the snake is overlapping the body
     *
     * @return true if the head overlaps any part of the body
     */
    private boolean hasCrashedIntoBody() {
        for (int i = 0; i < this.bodyParts.size(); i++) { // - 1 as the tail will be out of the way by time the next frame renders
            if (this.position.equals(this.bodyParts.get(i)))
                return true;
        }
        return false;
    }

    @Override
    public void onCollision(SimpleCollisionEvent sce) {
        if (sce.otherCollidable() instanceof Food)
            bodyParts.add(Vector2.newFrom(this.tailLastPosition));
    }
    
    private enum EMovementDirection {
        LEFT,
        UP,
        RIGHT,
        DOWN,
    };
}
