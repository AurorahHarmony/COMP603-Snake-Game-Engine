package net.scriptronix.snakegame.rendering;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import net.scriptronix.snakegame.Engine;
import net.scriptronix.snakegame.EngineConfig;
import net.scriptronix.snakegame.math.Vector2;
import net.scriptronix.snakegame.message.IMessageHandler;
import net.scriptronix.snakegame.message.Message;
import net.scriptronix.snakegame.world.Scene;

/**
 * Renders the game to a console
 */
public class ConsoleRenderer implements IRenderer, IMessageHandler {

    private Robot robot;
    private int screenWidth, screenHeight;
    private int outerScreenWidth, outerScreenHeight;
    private char[][] outputMatrix; // [height][width]
    private StringBuilder outString = new StringBuilder();

    public ConsoleRenderer(EngineConfig engingConfig) {
        this.screenWidth = engingConfig.getVirtualWidth();
        this.screenHeight = engingConfig.getVirtualHeight();
        this.outerScreenHeight = this.screenHeight + 2;
        this.outerScreenWidth = this.screenWidth + 2;

        Message.subscribe("SCREEN_RESIZED", this);

        this.initScreen();

        try {
            robot = new Robot();
        } catch (AWTException ex) {
            // Eat the exception. It is not neccessary to run
        }

    }

    @Override
    public void render(Scene scene) {
        this.outString.setLength(0);

        clearOutputMatix();
        scene.getSceneObjects().forEach((sceneObj) -> { // TODO: Refactor into its own function.
            if (sceneObj instanceof IConsoleRenderable) {
                IConsoleRenderable renderable = (IConsoleRenderable) sceneObj;

                for (ConsolePixel cPixel : renderable.getConsolePixels()) {
                    if (outsideScreen(cPixel)) // Cull out of bounds pixels
                        continue;

                    Vector2 pixelPos = cPixel.getPos();
                    this.outputMatrix[(int) pixelPos.getY() + 1][(int) pixelPos.getX() + 1] = cPixel.getSymbol();
                }
            }
        });

        clearScreen();
        for (char[] down : this.outputMatrix) {
            for (char colChar : down) {
                this.outString.append(colChar);
            }
            this.outString.append('\n');
        }
        System.out.println(this.outString);
    }

    /**
     * Checks to see whether a consolePixel is placed outside of the view-port
     *
     * @return
     */
    private boolean outsideScreen(ConsolePixel cPixel) {
        Vector2 pixelPos = cPixel.getPos();
        int pX = (int) pixelPos.getX();
        int pY = (int) pixelPos.getY();
        return pX < 0 || pY < 0 || pX > this.screenWidth - 1 || pY > this.screenHeight - 1;
    }

    /**
     * Initializes the outputMatrix, drawing a border around the screen.
     */
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
     * Clears the console, readying it for drawing the next frame
     */
    private void clearScreen() {
        if (System.console() != null)
            System.out.print("\033[H\033[2J");
        else { // There is no console. Fallback to simulating clear-screen command
            if (robot == null)
                return;
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_L);
            robot.keyRelease(KeyEvent.VK_L);
            robot.keyRelease(KeyEvent.VK_CONTROL);
        }
    }

    /**
     * Sets each pixel in the view portion of the output matrix to an empty
     * space character.
     */
    private void clearOutputMatix() {
        for (int i = 1; i < this.screenHeight + 1; i++) {
            for (int j = 1; j < this.screenWidth + 1; j++) {
                this.outputMatrix[i][j] = ' ';
            }
        }
    }

    @Override
    public void onMessage(Message msg) {
        if (msg.isCode("SCREEN_RESIZED")) {
            this.screenWidth = Engine.getInstance().getConfig().getVirtualWidth();
            this.screenHeight = Engine.getInstance().getConfig().getVirtualHeight();
            this.outerScreenHeight = this.screenHeight + 2;
            this.outerScreenWidth = this.screenWidth + 2;
            
            this.initScreen();
        }
    }

}
