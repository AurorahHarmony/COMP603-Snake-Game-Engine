package net.scriptronix.snakegame.game;

/**
 * Manages the scoreboard
 */
public class ScoreBoard {

    /**
     * @return the last saved score.
     */
    public static Integer getLastScore() {
        ScoreModel lastScore = ScoreModel.getLastScore();

        if (lastScore == null)
            return null;
        return lastScore.getScore();
    }

    /**
     * @return the last saved score as a String. If there is no Score, "None"
     * will be returned.
     */
    public static String getLastScoreString() {
        Integer lastScore = getLastScore();
        return (lastScore == null) ? "None" : lastScore.toString();
    }

    /**
     * @return the highest score.
     */
    public static Integer getHighScore() {
        ScoreModel highScore = ScoreModel.getHighScore();

        if (highScore == null)
            return null;
        return highScore.getScore();
    }

    /**
     * @return the highest score as a String. If there is no HighScore, "None"
     * will be returned.
     */
    public static String getHighScoreString() {
        Integer highScore = getHighScore();
        return (highScore == null) ? "None" : highScore.toString();
    }

    /**
     * Add a new score to the database
     *
     * @param score The score to add
     */
    public static void addNewScore(int score) {
        ScoreModel.createScore(score);

    }
}
