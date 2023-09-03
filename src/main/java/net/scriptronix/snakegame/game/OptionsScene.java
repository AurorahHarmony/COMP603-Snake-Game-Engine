package net.scriptronix.snakegame.game;

import net.scriptronix.snakegame.world.Scene;

/**
 * Options Screen
 */
public class OptionsScene  extends Scene {

    public OptionsScene() {
        this.spawnObject(new OptionsMenu(this));
    }
    
}