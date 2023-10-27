package net.scriptronix.snakegame.game;

import java.util.ArrayList;
import java.util.HashMap;
import net.scriptronix.snakegame.assets.BaseModel;
import net.scriptronix.snakegame.assets.FieldMetadata;

/**
 * Score model
 */
final public class ScoreModel extends BaseModel {

    @FieldMetadata(sqlType = "INT")
    private int score;

    protected ScoreModel() {
        super();
    }

    protected ScoreModel(int id) {
        super(id);
    }

    /**
     * @return The name of this table
     */
    public static String getTableName() {
        return "scores";
    }

    /**
     * @return The score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Set the score
     *
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets the last score entered into the database
     *
     * @return ScoreModel instance
     */
    public static ScoreModel getLastScore() {
        initTable(ScoreModel.class);

        ArrayList<HashMap<String, Object>> scores = dbLoader.getOrderedRows(ScoreModel.getTableName(), "id", 1);

        if (scores.size() < 1)
            return null;

        HashMap<String, Object> score = scores.get(0);

        ScoreModel scoreModel = new ScoreModel();
        scoreModel.setId((Integer) score.getOrDefault("ID", null));
        scoreModel.score = (Integer) score.getOrDefault("SCORE", null);

        return scoreModel;
    }

    /**
     * Gets the highest score from the database
     *
     * @return ScoreModel Instance
     */
    public static ScoreModel getHighScore() {
        initTable(ScoreModel.class);

        ArrayList<HashMap<String, Object>> scores = dbLoader.getOrderedRows(ScoreModel.getTableName(), "score", 1);

        if (scores.size() < 1)
            return null;

        HashMap<String, Object> score = scores.get(0);

        ScoreModel scoreModel = new ScoreModel();
        scoreModel.setId((Integer) score.getOrDefault("ID", null));
        scoreModel.score = (Integer) score.getOrDefault("SCORE", null);

        return scoreModel;
    }

    /**
     * Creates a new Score
     *
     * @param newScore
     * @return
     */
    public static ScoreModel createScore(int newScore) {
        ScoreModel scoreModel = new ScoreModel();
        scoreModel.score = newScore;
        scoreModel.save();
        return new ScoreModel();
    }
}
