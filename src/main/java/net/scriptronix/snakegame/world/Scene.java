package net.scriptronix.snakegame.world;

import java.util.ArrayList;
import net.scriptronix.snakegame.game.Snake;

/**
 * Container for all objects within a scene
 */
public class Scene {
    private ArrayList<SceneObject> sceneObjects = new ArrayList<>();

    public Scene() {
        this.sceneObjects.add(new Snake());
    }
    
    /**
     * @return ArrayList of Scene Objects
     */
    public ArrayList<SceneObject> getSceneObjects() {
        return this.sceneObjects;
    }

    /**
     * Run the update function on each object in the scene
     */
    public void update() {
        for (SceneObject obj : this.sceneObjects) {
            obj.update();
        }
    }
    
    
}
