package net.scriptronix.snakegame.game;

import java.util.Random;
import net.scriptronix.snakegame.EngineConfig;
import net.scriptronix.snakegame.math.Vector2;
import net.scriptronix.snakegame.message.Message;
import net.scriptronix.snakegame.rendering.ConsolePixel;
import net.scriptronix.snakegame.rendering.IConsoleRenderable;
import net.scriptronix.snakegame.world.ISimpleCollidable;
import net.scriptronix.snakegame.world.Scene;
import net.scriptronix.snakegame.world.SceneObject;
import net.scriptronix.snakegame.world.SimpleCollisionEvent;

/**
 * Represents a piece of food that the snake can eat
 */
public class Food extends SceneObject implements IConsoleRenderable, ISimpleCollidable {

    private final char symbol = '@';
    private final Random rand = new Random();

    public Food(Scene scene) {
        super(scene);
        this.teleportToRandomLocation();
    }

    @Override
    public ConsolePixel[] getConsolePixels() {
        return new ConsolePixel[]{new ConsolePixel(position, symbol)};
    }

    @Override
    public void onCollision(SimpleCollisionEvent sce) {
        if (sce.otherCollidable() instanceof Snake) {
            Message.send("INCREMENT_SCORE", this, null);
            teleportToRandomLocation();
        }
    }

    /**
     * Teleport the food to a new random location on screen
     */
    private void teleportToRandomLocation() {
        EngineConfig ec = this.scene.getEngineInstance().getConfig();
        int playAreaWidth = ec.getVirtualWidth();
        int playAreaHeight = ec.getVirtualHeight();
        this.position.setX(rand.nextInt(playAreaWidth + 1));
        this.position.setY(rand.nextInt(playAreaHeight + 1));
    }

}
