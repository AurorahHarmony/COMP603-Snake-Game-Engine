package net.scriptronix.snakegame.game;

import net.scriptronix.snakegame.assets.BaseModel;
import net.scriptronix.snakegame.assets.FieldMetadata;

/**
 *
 * @author minee
 */
public class ScoreModel extends BaseModel{
    
    @FieldMetadata(sqlType = "INT")
    private int score;
    
    @Override
    public String getTableName() {
        return "Score";
    }

    public ScoreModel(int score) {
        this.score = score;
    }
    
}
