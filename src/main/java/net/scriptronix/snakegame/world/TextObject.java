package net.scriptronix.snakegame.world;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import net.scriptronix.snakegame.rendering.ISwingRenderable;

/**
 * Displays a piece of text in the Scene
 */
public class TextObject extends SceneObject implements ISwingRenderable {

    private String displayText;

    public TextObject(Scene scene, String text) {
        super(scene);

        this.displayText = text;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void draw(Graphics g, ImageObserver obeserver, int scaleFactor) {
        g.drawString(this.displayText, this.position.getX() * scaleFactor, (this.position.getY() * scaleFactor) + scaleFactor);
    }

}
