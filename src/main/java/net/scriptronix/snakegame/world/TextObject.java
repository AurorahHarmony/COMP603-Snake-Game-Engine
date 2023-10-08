package net.scriptronix.snakegame.world;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import net.scriptronix.snakegame.math.Vector2;
import net.scriptronix.snakegame.rendering.ConsolePixel;
import net.scriptronix.snakegame.rendering.IConsoleRenderable;
import net.scriptronix.snakegame.rendering.ISwingRenderable;

/**
 * Displays a piece of text in the Scene
 */
public class TextObject extends SceneObject implements IConsoleRenderable, ISwingRenderable {

    private String displayText;

    public TextObject(Scene scene, String text) {
        super(scene);

        this.displayText = text;
    }

    @Override
    public ConsolePixel[] getConsolePixels() {
        ArrayList<ConsolePixel> pixels = new ArrayList<>();
        Vector2 pixelPosition;

        char[] chars = this.displayText.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            pixelPosition = Vector2.newFrom(this.position);
            pixelPosition.add(i, 0); // Offset one space right to allow space for the cursor
            pixels.add(new ConsolePixel(pixelPosition, chars[i]));
        }

        return pixels.toArray(ConsolePixel[]::new);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void draw(Graphics g, ImageObserver obeserver, int scaleFactor) {
        g.drawString(this.displayText, this.position.getX() * scaleFactor, this.position.getY() * scaleFactor);
    }

}
