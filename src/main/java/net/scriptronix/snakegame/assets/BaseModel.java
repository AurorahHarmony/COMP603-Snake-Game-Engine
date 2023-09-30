package net.scriptronix.snakegame.assets;

import java.lang.reflect.Field;
import java.util.ArrayList;
import net.scriptronix.snakegame.Engine;

/**
 * Abstract class to provide functionality for Models to access the database.
 */
abstract public class BaseModel {
   final DatabaseLoader dbLoader = Engine.getInstance().getDBLoader();
    
   public abstract String getTableName(); 
   
   public void save() {
       this.initTable();
   }
   
   /**
    * Initializes this model in the database.
    */
   private void initTable() {
       if (dbLoader.checkTableExists(this.getTableName())) {
           return;
       }
       
       ArrayList<DatabaseFieldStruct> fields = new ArrayList<>();

       for (Field field : this.getClass().getDeclaredFields()) {
           field.setAccessible(true);
           
           FieldMetadata metadata = field.getAnnotation(FieldMetadata.class);
           if (metadata != null) {
               DatabaseFieldStruct dbfs = new DatabaseFieldStruct();
               dbfs.fieldName = field.getName();
               dbfs.sqlType = metadata.sqlType();
               fields.add(dbfs);
           }
       }
       dbLoader.createTable(this.getTableName(), fields);
       
   }
}
