package net.scriptronix.snakegame.assets;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows the definition of field types in the database using annotations.
 */

// Specifying that this annotation can be applied to fields
@Target(ElementType.FIELD)
// Specifying that this annotation should be available at runtime
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldMetadata {
    // Declaring an attribute of the annotation for storing SQL type information
    String sqlType();
}
