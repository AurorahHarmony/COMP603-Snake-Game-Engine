package net.scriptronix.snakegame.assets;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Facilitates communication with the database
 */
public class DatabaseLoader {

    final private Connection connection;

    /**
     * Stores the config a database loader
     */
    final private DatabaseLoaderConfig config;

    /**
     * Constructs a new instance of the DatabaseLoader using the provided config
     *
     * @param config
     */
    public DatabaseLoader(DatabaseLoaderConfig config) throws SQLException {
        this.config = config;

        this.connection = DriverManager.getConnection(this.config.url, this.config.username, this.config.password);
    }

    public Connection getConnection() {
        return this.connection;
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

    /**
     * Updates a row in the database by its ID
     *
     * @param id ID of the row the update.
     * @param tableName The name of the table to update
     * @param values A HashMap where the key is the column name and the value is
     * the value to set.
     */
    public void updateRow(String tableName, int id, HashMap<String, Object> values) {
        StringBuilder setters = new StringBuilder();
        ArrayList<Object> valueList = new ArrayList();

        for (String column : values.keySet()) {
            setters.append(column).append(" = ?,");
            valueList.add(values.get(column));
        }

        // Remove trailing comma
        if (setters.length() > 0)
            setters.deleteCharAt(setters.length() - 1);

        // Run the update
        String sql = "UPDATE " + tableName + " SET " + setters + " WHERE id = ?";

        try ( PreparedStatement pstmt = this.connection.prepareStatement(sql)) {

            for (int i = 0; i < valueList.size(); i++) {
                pstmt.setObject(i + 1, valueList.get(i));
            }
            pstmt.setObject(valueList.size() + 1, values.get("id"));
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Insert a new row into the database
     *
     * @param tableName The name of the table to insert into
     * @param values A HashMap where the key is the column name and the value is
     * the value to set.
     */
    public void insertRow(String tableName, HashMap<String, Object> values) {
        StringBuilder columns = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();
        ArrayList<Object> valueList = new ArrayList();

        for (String column : values.keySet()) {
            columns.append(column).append(",");
            placeholders.append("?,");
            valueList.add(values.get(column));
        }

        // Remove the last commas
        if (columns.length() > 0) {
            columns.deleteCharAt(columns.length() - 1);
            placeholders.deleteCharAt(placeholders.length() - 1);
        }

        // Run the insert
        String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";

        try ( PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            for (int i = 0; i < valueList.size(); i++) {
                pstmt.setObject(i + 1, valueList.get(i));
            }
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get a row by its ID.
     *
     * @param tableName Name of the table to search.
     * @param id ID of the row to find.
     * @return The row stored in a HashMap or null if no row was found.
     */
    public HashMap<String, Object> getRowById(String tableName, int id) {
        String sql = "SELECT * FROM " + tableName + " WHERE id = ?";

        try ( PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try ( ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnCount = rsmd.getColumnCount();

                    HashMap<String, Object> row = new HashMap<>();

                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = rsmd.getColumnName(i);
                        Object columnValue = rs.getObject(i);
                        row.put(columnName, columnValue);
                    }

                    return row;
                } else
                    return null;
            }
        } catch (SQLException ex) {
            return null;
        }
    }

    /**
     * Defines whether a database query should be sorted in ASCENDING or
     * DESCENDING order.
     */
    public enum ESortDirection {
        ASCENDING("ASC"),
        DESCENDING("DESC");

        private final String sqlRepresentation;

        ESortDirection(String sqlRepresentation) {
            this.sqlRepresentation = sqlRepresentation;
        }

        public String sqlRepresentation() {
            return this.sqlRepresentation;
        }
    }


    /**
     * Get ordered rows from a table in Descending order.
     *
     * @param tableName The name of the table to get the rows from
     * @param column The column to order by
     * @return An ArrayList of Rows
     */
    public ArrayList<HashMap<String, Object>> getOrderedRows(String tableName, String column) {
        return getOrderedRows(tableName, column, ESortDirection.DESCENDING);
    }

    /**
     * Get ordered rows from a table
     *
     * @param tableName The name of the table to get the rows from
     * @param column The column to order by
     * @param direction Sort direction. Ascending or Descending.
     * @return An ArrayList of Rows
     */
    public ArrayList<HashMap<String, Object>> getOrderedRows(String tableName, String column, ESortDirection direction) {
        ArrayList<HashMap<String, Object>> rows = new ArrayList<>();

        String sql = "SELECT * FROM " + tableName + " ORDER BY " + column + " " + direction.sqlRepresentation();

        try {
            PreparedStatement pstmt = this.connection.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while (rs.next()) {
                HashMap<String, Object> row = new HashMap<>();

                // Iterate through columns
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = rsmd.getColumnName(i);
                    Object columnValue = rs.getObject(i);
                    row.put(columnName, columnValue);
                }

                rows.add(row);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rows;
    }
}
