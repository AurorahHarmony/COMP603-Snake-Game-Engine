package net.scriptronix.snakegame.rendering;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import net.scriptronix.snakegame.game.GameState;

/**
 * Renders the game to a console
 */
public class ConsoleRenderer implements IRenderer {

    private Robot robot;
    private final int screenWidth, screenHeight;
    private final int outerScreenWidth, outerScreenHeight;
    private char[][] outputMatrix; // [height][width]
    private StringBuilder outString = new StringBuilder();

    public ConsoleRenderer() {
        this(30,10);
    }
    
    public ConsoleRenderer(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.outerScreenHeight = this.screenHeight + 2;
        this.outerScreenWidth = this.screenWidth + 2;
        
        this.initScreen();

        try {
            robot = new Robot();
        } catch (AWTException ex) {
            // Eat the exception. It is not neccessary to run
        }

    }

    @Override
    public void render(GameState gameState) {
        clearScreen();
        this.outString.setLength(0);

        for (char[] down : this.outputMatrix) {
            for (char colChar : down) {
                this.outString.append(colChar);
            }
            this.outString.append('\n');
        }
        System.out.println(this.outString);
    }

    private void initScreen() {
        this.outputMatrix = new char[outerScreenHeight][outerScreenWidth]; // Add 2 to make space for the borders

        clearOutputMatix();

        // Draws top and bottom borders.
        for (int i = 0; i < outerScreenHeight; i += this.screenHeight + 1) {
            for (int j = 0; j < outerScreenWidth; j++) {
                this.outputMatrix[i][j] = '#';
            }
        }

        for (int i = 1; i < this.screenHeight + 1; i++) {
            this.outputMatrix[i][0] = '#';
            this.outputMatrix[i][this.screenWidth + 1] = '#';
        }

    }

    /**
     * Clears the console.
     */
    private void clearScreen() {
        if (System.console() != null) {
            System.out.print("\033[H\033[2J");

        } else { // There is no console. Fallback to simulating clear-screen command
            if (robot == null) {
                return;
            }
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_L);
            robot.keyRelease(KeyEvent.VK_L);
            robot.keyRelease(KeyEvent.VK_CONTROL);
        }
    }

    private void clearOutputMatix() {
        for (int i = 0; i < this.screenHeight + 1; i++) {
            for (int j = 0; j < this.screenWidth + 1; j++) {
                this.outputMatrix[i][j] = ' ';
            }
        }
    }

}
