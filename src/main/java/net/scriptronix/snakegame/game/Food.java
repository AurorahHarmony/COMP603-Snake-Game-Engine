package net.scriptronix.snakegame.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.ImageObserver;
import java.util.Random;
import net.scriptronix.snakegame.EngineConfig;
import net.scriptronix.snakegame.message.Message;
import net.scriptronix.snakegame.rendering.ConsolePixel;
import net.scriptronix.snakegame.rendering.IConsoleRenderable;
import net.scriptronix.snakegame.rendering.ISwingRenderable;
import net.scriptronix.snakegame.world.ISimpleCollidable;
import net.scriptronix.snakegame.world.Scene;
import net.scriptronix.snakegame.world.SceneObject;
import net.scriptronix.snakegame.world.SimpleCollisionEvent;

/**
 * Represents a piece of food that the snake can eat
 */
public class Food extends SceneObject implements IConsoleRenderable, ISwingRenderable, ISimpleCollidable {

    private final char symbol = '%';
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
        this.position.setX(rand.nextInt(playAreaWidth));
        this.position.setY(rand.nextInt(playAreaHeight));
    }

    @Override
    public void destroy() {
    }

    /**
     * Draws the food (grapes)
     */
    @Override
    public void draw(Graphics g, ImageObserver observer, int scaleFactor) {

        Graphics2D g2 = (Graphics2D) g;

        int grapeSize = (int) (0.4 * scaleFactor);
        int centerX = this.position.getX() * scaleFactor - (grapeSize / 2);
        int centerY = this.position.getY() * scaleFactor - (grapeSize / 2);

        g2.setStroke(new BasicStroke(grapeSize / 3));
        drawVine(g2, grapeSize, centerX, centerY);
        drawGrapes(g2, grapeSize, centerX, centerY);
    }

    /**
     * Draw all of the grapes
     */
    private void drawGrapes(Graphics2D g2, int grapeSize, int centerX, int centerY) {
        int offset = (int) (grapeSize * 0.8);

        drawGrape(g2, new Color(90, 0, 90), centerX, centerY + offset, grapeSize);
        drawGrape(g2, new Color(110, 0, 110), centerX - (offset / 2), centerY, grapeSize);
        drawGrape(g2, new Color(110, 0, 110), centerX + (offset / 2), centerY, grapeSize);
        drawGrape(g2, new Color(140, 0, 140), centerX - offset, centerY - offset, grapeSize);
        drawGrape(g2, new Color(140, 0, 140), centerX + offset, centerY - offset, grapeSize);
        drawGrape(g2, new Color(150, 0, 150), centerX, centerY - offset, grapeSize);
    }

    /**
     * Draw a single grape
     */
    private void drawGrape(Graphics2D g2, Color color, int x, int y, int size) {
        g2.setColor(color);
        g2.fillOval(x, y, size, size);
    }

    /**
     * Draw the vine on the grapes
     */
    private void drawVine(Graphics2D g2, int grapeSize, int centerX, int centerY) {
        int vineStartY = centerY - (int) (grapeSize * 0.75);

        g2.setColor(new Color(0, 160, 0));
        g2.draw(new Line2D.Float(centerX + (grapeSize / 2), vineStartY, centerX, vineStartY - (grapeSize / 2)));
    }
}
