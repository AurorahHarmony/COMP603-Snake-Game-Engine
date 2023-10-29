package net.scriptronix.snakegame.world;

import java.util.ArrayList;
import java.util.Arrays;
import net.scriptronix.snakegame.Engine;

/**
 * Container for all objects within a scene
 */
public class Scene {

    protected ArrayList<SceneObject> sceneObjects = new ArrayList<>();
    
    /**
     * Spawns a new object in the scene
     * @param sceneObject The object to spawn in the scene
     */
    public void spawnObject(SceneObject sceneObject) {
        this.sceneObjects.add(sceneObject);
    }
    
    /**
     * Spawn multiple new objects in the scene.
     * @param sceneObjects 
     */
    public void spawnObjects(SceneObject ...sceneObjects) {
        this.sceneObjects.addAll(Arrays.asList(sceneObjects));
    }
    
    /**
     * @return A reference to the Engine instance
     */
    public Engine getEngineInstance() {
        return Engine.getInstance();
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
    
    /**
     * Cleanup the scene, before it is unloaded by the engine.
     */
    public void destroy() {
        for (SceneObject so : this.sceneObjects) {
            so.destroy();
        }
    }
}
