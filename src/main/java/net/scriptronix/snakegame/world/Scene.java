package net.scriptronix.snakegame.world;

import java.util.ArrayList;
import java.util.HashMap;
import net.scriptronix.snakegame.game.Food;
import net.scriptronix.snakegame.game.Snake;

/**
 * Container for all objects within a scene
 */
public class Scene {

    private ArrayList<SceneObject> sceneObjects = new ArrayList<>();
    private HashMap<String, ArrayList<ISimpleCollidable>> colliderTracking = new HashMap<>();

    public Scene() {
        this.sceneObjects.add(new Snake(this));
        this.sceneObjects.add(new Food(this));
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
        colliderTracking.clear();

        for (SceneObject obj : this.sceneObjects) {
            obj.update();

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
