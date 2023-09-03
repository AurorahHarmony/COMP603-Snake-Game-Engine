package net.scriptronix.snakegame.game;

import java.util.HashMap;
import net.scriptronix.snakegame.assets.KeyValueFileLoader;

/**
 * Manages the scoreboard
 */
public class ScoreBoard {

    final private static String SCOREBOARD_SAVE_LOCATION = "./scoreboard.txt";

    final private HashMap<String, String> lastSave;
    private int lastScore;
    private int highscore;

    public ScoreBoard() {
        this.lastSave = KeyValueFileLoader.loadFile(SCOREBOARD_SAVE_LOCATION);

        this.lastScore = Integer.parseInt(lastSave.getOrDefault("lastScore", "0"));
        this.highscore = Integer.parseInt(lastSave.getOrDefault("highScore", "0"));
    }

    /**
     * @return the last saved score
     */
    public int getLastScore() {
        return this.lastScore;
    }

    public int getHighScore() {
        return this.highscore;
    }

    public void addNewScore(int score) {
        this.lastScore = score;
        if (score > highscore)
            this.highscore = score;
        this.save();
    }

    private void save() {
        lastSave.put("lastScore", Integer.toString(lastScore));
        lastSave.put("highScore", Integer.toString(highscore));

        KeyValueFileLoader.saveFile(lastSave, SCOREBOARD_SAVE_LOCATION);
    }

}
