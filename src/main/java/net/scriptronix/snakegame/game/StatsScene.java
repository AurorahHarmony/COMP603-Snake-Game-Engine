package net.scriptronix.snakegame.game;

import net.scriptronix.snakegame.world.Scene;
import net.scriptronix.snakegame.world.TextObject;

/**
 * Shows stats to the player about their previous games
 */
public class StatsScene extends Scene {

    public StatsScene() {

        ScoreBoard scoreBoard = new ScoreBoard();

        TextObject scoreText = new TextObject(this, "Game Stats: ");
        this.spawnObject(scoreText);

        TextObject lastScoreText = new TextObject(this, "Your last Score: " +
                Integer.toString(scoreBoard.getLastScore()));
        lastScoreText.getPosition().setY(1);
        this.spawnObject(lastScoreText);

        TextObject highScoreText = new TextObject(this, "Highest Score: " +
                Integer.toString(scoreBoard.getHighScore()));
        highScoreText.getPosition().setY(2);
        this.spawnObject(highScoreText);

        MainMenuButton mainMenuButton = new MainMenuButton(this);
        mainMenuButton.getPosition().setY(4);
        this.spawnObject(mainMenuButton);
    }
}
