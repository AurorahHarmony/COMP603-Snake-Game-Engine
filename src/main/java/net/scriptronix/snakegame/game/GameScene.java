package net.scriptronix.snakegame.game;

import net.scriptronix.snakegame.Engine;
import net.scriptronix.snakegame.world.Scene;

/**
 * The main game scene
 */
public class GameScene extends Scene {

    public GameScene(Engine engine) {
        super(engine);
        
        this.spawnObject(new Snake(this));
        this.spawnObject(new Food(this));
    }

}
