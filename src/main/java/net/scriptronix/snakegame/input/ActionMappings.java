package net.scriptronix.snakegame.input;

import java.util.HashMap;

/**
 * Maps input codes to actions
 */
public class ActionMappings {

    private static final HashMap<Integer, EInputAction> mappings = new HashMap<>() {{
        put(30, EInputAction.MOVE_LEFT);
        put(17, EInputAction.MOVE_UP);
        put(32, EInputAction.MOVE_RIGHT);
        put(31, EInputAction.MOVE_DOWN);
    }};

    public static EInputAction getAction(int keyCode) {
        EInputAction action;
        if ((action = mappings.get(keyCode)) != null) {
            return action;
        }
        return null;
    }
}
