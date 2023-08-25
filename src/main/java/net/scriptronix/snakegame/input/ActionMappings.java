package net.scriptronix.snakegame.input;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import java.util.HashMap;

/**
 * Maps input codes to actions
 */
public class ActionMappings {

    private static final HashMap<Integer, EInputAction> mappings = new HashMap<>() {{
        put(NativeKeyEvent.VC_A, EInputAction.MOVE_LEFT);
        put(NativeKeyEvent.VC_W, EInputAction.MOVE_UP);
        put(NativeKeyEvent.VC_D, EInputAction.MOVE_RIGHT);
        put(NativeKeyEvent.VC_S, EInputAction.MOVE_DOWN);
        put(NativeKeyEvent.VC_ESCAPE, EInputAction.QUIT);
    }};

    public static EInputAction getAction(int keyCode) {
        EInputAction action;
        if ((action = mappings.get(keyCode)) != null) {
            return action;
        }
        return null;
    }
}
