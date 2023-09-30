package net.scriptronix.snakegame.assets;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Facilitates communication with the database
 */
public class DatabaseLoader {

    Connection connection = null;

    /**
     * Stores the config a database loader
     */
    DatabaseLoaderConfig config;

    /**
     * Constructs a new instance of the DatabaseLoader using the provided config
     *
     * @param config
     */
    public DatabaseLoader(DatabaseLoaderConfig config) {
        this.config = config;

        try {
            this.connectDB();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initiates a connection to the database
     *
     * @throws SQLException
     */
    private void connectDB() throws SQLException {
        this.connection = DriverManager.getConnection(this.config.url, this.config.username, this.config.password);
    }

    /**
     * Creates a new table if it does not already exist
     *
     * @param tableName
     * @param fields
     */
    public void createTable(String tableName, ArrayList<DatabaseFieldStruct> fields) {
        try ( Statement stmt = this.connection.createStatement()) {
            StringBuilder sql = new StringBuilder("CREATE TABLE " + tableName + " (");

            for (int i = 0; i < fields.size(); i++) {
                DatabaseFieldStruct field = fields.get(i);
                sql.append(field.fieldName).append(" ").append(field.sqlType);

                if (i < fields.size() - 1)
                    sql.append(",");
            }

            sql.append(")");

            stmt.executeUpdate(sql.toString());
        } catch (SQLException e) {
        }
    }

    /**
     * Checks whether the provided table exists
     *
     * @param tableName The name of the table to check
     * @return true if the table already exists
     */
    public boolean checkTableExists(String tableName) {
        boolean tableExists = false;

        try {
            DatabaseMetaData dbmd = this.connection.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);

            while (rsDBMeta.next()) {
                String foundTableName = rsDBMeta.getString("TABLE_NAME");
                if (foundTableName.equalsIgnoreCase(tableName))
                    tableExists = true;
            }

            if (rsDBMeta != null)
                rsDBMeta.close();
        } catch (SQLException ex) {
        }

        return tableExists;
    }
}
