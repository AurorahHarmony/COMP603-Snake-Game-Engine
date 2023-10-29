package net.scriptronix.snakegame.game;

import net.scriptronix.snakegame.Engine;
import net.scriptronix.snakegame.EngineConfig;
import net.scriptronix.snakegame.world.Scene;
import net.scriptronix.snakegame.world.TextObject;

/**
 * Main menu of the game. This is the first scene that is loaded.
 */
public class MainMenuScene extends Scene {

    public MainMenuScene() {
        EngineConfig ec = Engine.getInstance().getConfig();
        ec.setFPS(60);
        
        this.spawnObject(new MainMenu(this));

        TextObject controlHelpTitle = new TextObject(this, "Controls:");
        controlHelpTitle.getPosition().setY(6);
        TextObject controlHelpMove = new TextObject(this, "Move: W,A,S,D");
        controlHelpMove.getPosition().setX(1).setY(7);
        TextObject controlHelpSelect = new TextObject(this, "Select: Enter");
        controlHelpSelect.getPosition().setX(1).setY(8);
        TextObject controlHelpQuit = new TextObject(this, "Quit: Escape");
        controlHelpQuit.getPosition().setX(1).setY(9);
        this.spawnObjects(controlHelpTitle, controlHelpMove, controlHelpSelect, controlHelpQuit);
    }

}
