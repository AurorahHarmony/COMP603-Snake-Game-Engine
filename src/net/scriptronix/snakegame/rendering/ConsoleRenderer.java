package net.scriptronix.snakegame.rendering;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.scriptronix.snakegame.game.GameState;

/**
 * Renders the game to a console
 */
public class ConsoleRenderer implements IRenderer {

    Robot robot;
    String test = "";
    
    public ConsoleRenderer() {
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            // Eat the exception. It is not neccessary to run
        }

    }

    @Override
    public void render(GameState gameState) {
        clearScreen();
        test += "=";
        System.out.println(test);

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

}
