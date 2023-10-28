package net.scriptronix.snakegame.game;

import net.scriptronix.snakegame.world.Scene;
import net.scriptronix.snakegame.world.TextObject;

/**
 * Shows stats to the player about their previous games
 */
public class StatsScene extends Scene {

    public StatsScene() {

        TextObject scoreText = new TextObject(this, "Game Stats: ");
        this.spawnObject(scoreText);

        TextObject lastScoreText = new TextObject(this, "Your last Score: " +
                ScoreBoard.getLastScoreString());
        lastScoreText.getPosition().setY(1);
        this.spawnObject(lastScoreText);

        TextObject highScoreText = new TextObject(this, "Highest Score: " +
                ScoreBoard.getHighScoreString());
        highScoreText.getPosition().setY(2);
        this.spawnObject(highScoreText);

        MainMenuButton mainMenuButton = new MainMenuButton(this);
        mainMenuButton.getPosition().setY(4);
        this.spawnObject(mainMenuButton);
    }
}
