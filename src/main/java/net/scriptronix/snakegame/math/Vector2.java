package net.scriptronix.snakegame.math;

/**
 * Structure for storing Vector2 data
 */
public class Vector2 {
    private float x;
    private float y;
    
    /**
     * Creates a Vector2(x = 0, y = 0)
     */
    public Vector2() {
        this(0, 0);
    }
    
    /**
     * Create a Vector2
     * @param x
     * @param y 
     */
    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the x position
     */
    public float getX() {
        return x;
    }

    /**
     * @param x the x position to set
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * @return the y position
     */
    public float getY() {
        return y;
    }

    /**
     * @param y the y position to set
     */
    public void setY(float y) {
        this.y = y;
    }
    
    /**
     * Add the values from another Vector2 to this Vector2
     * @param vec 
     */
    public void add(Vector2 vec) {
        this.x += vec.x;
        this.y += vec.y;
    }
    
    /**
     * Creates a new zero Vector2 (0,0)
     * @return 
     */
    public static Vector2 zero() {
        return new Vector2(0,0);
    }
    
    /**
     * Copy the values from another Vector2 into this Vector2
     * @param vector2 The Vector2 to copy from
     */
    public void copyFrom(Vector2 vector2) {
        this.x = vector2.x;
        this.y = vector2.y;
    }
    
}
