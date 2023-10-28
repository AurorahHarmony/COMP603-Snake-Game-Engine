package net.scriptronix.snakegame.assets;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.scriptronix.snakegame.Engine;

/**
 * Abstract class to provide functionality for Models to access the database.
 * Each instance represents a single database row.
 */
abstract public class BaseModel {

    /**
     * A Reference to the Engine's dbLoader
     */
    final static protected DatabaseLoader dbLoader = Engine.getInstance().getDBLoader();

    /**
     * The ID of this row. -1 if this is a new row that should be inserted.
     */
    private int id;

    /**
     * Create a new instance of the Model
     */
    protected BaseModel() {
        this(-1);
    }

    /**
     * Create a new instance of the Model, represents and EXISTING database row.
     *
     * @param id
     */
    protected BaseModel(int id) {
        this.id = id;
    }

    /**
     * The name of the table for this model. This is a template function and
     * MUST be overriden for the model to work.
     *
     * @return
     */
    static public String getTableName() {
        return null;
    }

    /**
     * Get the ID of this Model
     *
     * @return the ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * Set the ID of this Model.
     *
     * @param id
     */
    protected void setId(int id) {
        this.id = id;
    }

    /**
     * Save this model instance to the database
     */
    public void save() {
        initTable(this.getClass());

        HashMap<String, Object> values = new HashMap<>();

        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            FieldMetadata metadata = field.getAnnotation(FieldMetadata.class);
            if (metadata != null)
                try {
                values.put(field.getName(), field.get(this));
            } catch (IllegalAccessException ex) {
                Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String tableName = getTableNameFromClass(this.getClass());

        if (isInDatabase())
            dbLoader.updateRow(tableName, this.id, values);
        else
            dbLoader.insertRow(tableName, values); // TODO: Set the ID
    }

    private boolean isInDatabase() {
        if (this.id < 0)
            return false;

        String tableName = getTableNameFromClass(this.getClass());
        HashMap<String, Object> foundRow = dbLoader.getRowById(tableName, this.id);
        return foundRow != null;
    }

    /**
     * Initializes this model in the database.
     *
     * @param modelClass
     */
    protected static void initTable(Class<? extends BaseModel> modelClass) {
        String tableName = getTableNameFromClass(modelClass);

        if (dbLoader.checkTableExists(tableName))
            return;

        ArrayList<DatabaseFieldStruct> fields = new ArrayList<>();
        fields.add(new DatabaseFieldStruct("id", "INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY"));

        for (Field field : modelClass.getDeclaredFields()) {
            field.setAccessible(true);

            FieldMetadata metadata = field.getAnnotation(FieldMetadata.class);
            if (metadata != null) {
                DatabaseFieldStruct dbfs = new DatabaseFieldStruct();
                dbfs.fieldName = field.getName();
                dbfs.sqlType = metadata.sqlType();
                fields.add(dbfs);
            }
        }

        dbLoader.createTable(tableName, fields);
    }

    /**
     * Get the table name from any BaseModel subclass
     *
     * @param modelClass
     * @return The name of the table for the passed in BaseModel subclass
     */
    private static String getTableNameFromClass(Class<? extends BaseModel> modelClass) {
        String tableName = null;
        try {
            Method tableNameField = modelClass.getMethod("getTableName");
            tableName = (String) tableNameField.invoke(null);

            if (tableName == null || tableName.equals(""))
                throw new UnsupportedOperationException("getTableName() method must be implemented in the child class.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to get tableName: " + e.getMessage());
        }
        return tableName;
    }

}
