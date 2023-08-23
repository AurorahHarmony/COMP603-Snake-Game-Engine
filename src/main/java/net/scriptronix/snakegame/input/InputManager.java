package net.scriptronix.snakegame.input;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles application inputs and notifies the rest of the application about
 * inputs.
 */
public class InputManager implements NativeKeyListener {

    private static boolean[] keys;

    private InputManager() {
    } // Static class should not be constructed

    public static void initialize() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            Logger.getLogger(InputManager.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Cannot register input hook");
            System.exit(1); // We can not run without input
        }

        GlobalScreen.addNativeKeyListener(new InputManager());

        keys = new boolean[255];
        for (int i = 0; i < 255; i++) {
            InputManager.keys[i] = false;
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() > 255) {
            return;
        }

        InputManager.keys[e.getKeyCode()] = true;
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() > 255) {
            return;
        }
        InputManager.keys[e.getKeyCode()] = false;

    }
}
