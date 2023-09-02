package net.scriptronix.snakegame.world;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import net.scriptronix.snakegame.Engine;

/**
 * Dynamically creates scene instances based on the fully qualified class name,
 * using reflection
 */
public class SceneFactory {

    public static Scene createScene(String className, Engine engine) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalArgumentException, IllegalAccessException {
        Class<?> sceneClass = Class.forName(className);

        if (!Scene.class.isAssignableFrom(sceneClass))
            throw new IllegalArgumentException("The provided class does not extend Scene.");

        Constructor<?> ctor = sceneClass.getConstructor(Engine.class);
        return (Scene) ctor.newInstance(engine);

    }
}
