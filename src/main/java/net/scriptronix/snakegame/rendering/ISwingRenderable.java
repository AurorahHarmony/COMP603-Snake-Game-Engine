package net.scriptronix.snakegame.rendering;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

/**
 * Interface defining a object which can be rendered by the swing renderer.
 */
public interface ISwingRenderable {
    /**
     * Draw the object, using the Graphics class
     * @param g
     * @param obeserver 
     */
    void draw(Graphics g, ImageObserver obeserver, int scaleFactor);
}
