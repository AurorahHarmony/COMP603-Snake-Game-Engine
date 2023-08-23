package net.scriptronix.snakegame.rendering;

import net.scriptronix.snakegame.math.Vector2;

/**
 * Defines the structure required to render an object to the console renderer
 */
public class ConsolePixel {
    private final Vector2 location;
    private final char symbol;

    /**
     * Creates a new ConsolePixel
     * @param location
     * @param symbol 
     */
    public ConsolePixel(Vector2 location, char symbol) {
        this.location = location;
        this.symbol = symbol;
    }
    
    /**
     * @return the position of this pixel
     */
    public Vector2 getPos() {
        return location;
    }

    /**
     * @return the character or symbol used in this pixel
     */
    public char getSymbol() {
        return symbol;
    }    
    
}
