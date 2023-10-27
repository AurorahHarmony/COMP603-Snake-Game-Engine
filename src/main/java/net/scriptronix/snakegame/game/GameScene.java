package net.scriptronix.snakegame.game;

import java.util.ArrayList;
import java.util.HashMap;
import net.scriptronix.snakegame.world.ISimpleCollidable;
import net.scriptronix.snakegame.world.Scene;
import net.scriptronix.snakegame.world.SceneObject;
import net.scriptronix.snakegame.world.SimpleCollisionEvent;
import net.scriptronix.snakegame.world.TextObject;

/**
 * The main game scene
 */
public class GameScene extends Scene {

    private final HashMap<String, ArrayList<ISimpleCollidable>> colliderTracking = new HashMap<>();
    private boolean gameActive;

    public GameScene() {
        this.spawnObject(new Snake(this));
        this.spawnObject(new Food(this));
        this.gameActive = true;
    }

    /**
     * Show the gameOver screen
     * @param score 
     */
    public void gameOver(int score) {
        this.gameActive = false;
        this.sceneObjects.clear();
        
        TextObject scoreText = new TextObject(this, "You Scored: " + Integer.toString(score));
        this.spawnObject(scoreText);
        
        TextObject lastScoreText = new TextObject(this, "Your last Score: " +  
                ScoreBoard.getLastScoreString());
        lastScoreText.getPosition().setY(1);
        this.spawnObject(lastScoreText);
        
        ScoreBoard.addNewScore(score);
        
        TextObject highScoreText = new TextObject(this, "Highest Score: " +  
                ScoreBoard.getHighScoreString());
        highScoreText.getPosition().setY(2);
        this.spawnObject(highScoreText);

        MainMenuButton mainMenuButton = new MainMenuButton(this);
        mainMenuButton.getPosition().setY(4);
        this.spawnObject(mainMenuButton);
    }

    @Override
    public void update() {

        super.update();
        colliderTracking.clear();

        for (SceneObject obj : this.sceneObjects) {
            obj.update();

            if (!gameActive) // No need to update the collisions anymore.
                return;

            if (obj instanceof ISimpleCollidable) // Generates a hashmap of collisions relevant to the Object's locations
                colliderTracking.computeIfAbsent(obj.getPosition().toString(),
                        k -> new ArrayList<ISimpleCollidable>())
                        .add((ISimpleCollidable) obj);

        }

        this.broadCastCollisions();
    }

    /**
     * Runs the onCollision function in any objects that are experiencing a
     * collision.
     */
    private void broadCastCollisions() {
        for (HashMap.Entry<String, ArrayList<ISimpleCollidable>> entry : colliderTracking.entrySet()) {
            ArrayList<ISimpleCollidable> objArr = entry.getValue();

            if (objArr.size() < 2)
                continue;

            for (int i = 0; i < objArr.size(); i++) {
                for (int j = 0; j < objArr.size(); j++) {
                    if (i == j)
                        continue;
                    objArr.get(i).onCollision(new SimpleCollisionEvent(objArr.get(j)));
                }
            }
        }

    }

}
