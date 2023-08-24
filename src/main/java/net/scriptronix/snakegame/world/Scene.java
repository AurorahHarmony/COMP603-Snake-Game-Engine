package net.scriptronix.snakegame.world;

import java.util.ArrayList;

/**
 * Container for all objects within a scene
 */
public class Scene {
    private ArrayList<SceneObject> sceneObjects = new ArrayList<>();

    public Scene() {
        this.sceneObjects.add(new SceneObject());
    }
    
    /**
     * @return ArrayList of Scene Objects
     */
    public ArrayList<SceneObject> getSceneObjects() {
        return this.sceneObjects;
    }
    
    
}
