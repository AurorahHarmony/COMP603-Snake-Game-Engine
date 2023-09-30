/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
    
    public static void main(String[] args) {
        ScoreModel sm = new ScoreModel(0);
        sm.save();
    }
    
}
