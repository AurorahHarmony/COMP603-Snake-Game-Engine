package net.scriptronix.snakegame.world;

import java.util.ArrayList;
import java.util.HashMap;
import net.scriptronix.snakegame.Engine;
import net.scriptronix.snakegame.game.Food;
import net.scriptronix.snakegame.game.Snake;

/**
 * Container for all objects within a scene
 */
public class Scene {

    final private Engine engine;
    protected ArrayList<SceneObject> sceneObjects = new ArrayList<>();

    public Scene(Engine engine) {
        this.engine = engine;
        
    }
    
    /**
     * Spawns a new object in the scene
     * @param sceneObject The object to spawn in the scene
     */
    public void spawnObject(SceneObject sceneObject) {
        this.sceneObjects.add(sceneObject);
    }
    
    /**
     * @return A reference to the Engine instance
     */
    public Engine getEngineInstance() {
        return this.engine;
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
    }

}
