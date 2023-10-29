/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package net.scriptronix.snakegame.assets;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author minee
 */
public class DatabaseLoaderTest {

    private DatabaseLoader dbLoader;

    @Before
    public void setUp() throws SQLException {
        DatabaseLoaderConfig dbConfig = new DatabaseLoaderConfig("jdbc:derby:memory:testdb;create=true", "test", "testpass");
        dbLoader = new DatabaseLoader(dbConfig);
    }

    @After
    public void tearDown() throws Exception {
        if (dbLoader != null)
            resetDatabase();
    }

    /**
     * Resets the database to its initial empty state.
     *
     * @throws SQLException
     */
    private void resetDatabase() throws SQLException {
        Statement stmt = null;
        try {
            Connection connection = dbLoader.getConnection();
            stmt = connection.createStatement();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, null, new String[]{"TABLE"});
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                stmt.executeUpdate("DROP TABLE " + tableName);
            }
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Creates a table in the database
     */
    private void createTable(String tableName) {
        ArrayList<DatabaseFieldStruct> fields = new ArrayList<>();
        fields.add(new DatabaseFieldStruct("id", "INT"));
        fields.add(new DatabaseFieldStruct("name", "VARCHAR(255)"));

        dbLoader.createTable(tableName, fields);
    }

    @Test
    public void testCreateTable() {
        assertFalse(dbLoader.checkTableExists("test_table"));
        this.createTable("test_table");
        assertTrue(dbLoader.checkTableExists("test_table"));
    }

    @Test
    public void testInsertAndRetrieveRow() {
        this.createTable("test_table");

        HashMap<String, Object> values = new HashMap<>();
        values.put("id", 1);
        values.put("name", "Test Name");
        dbLoader.insertRow("test_table", values);

        HashMap<String, Object> retrievedRow = dbLoader.getRowById("test_table", 1);
        assertNotNull(retrievedRow);
        assertEquals(1, retrievedRow.get("ID"));
        assertEquals("Test Name", retrievedRow.get("NAME"));
    }

    @Test
    public void testGetOrderedRows() {
        this.createTable("test_table");

        HashMap<String, Object> values = new HashMap<>();
        values.put("id", 1);
        values.put("name", "Test Name2");
        dbLoader.insertRow("test_table", values);

        HashMap<String, Object> values2 = new HashMap<>();
        values2.put("id", 2);
        values2.put("name", "Test Name2");
        dbLoader.insertRow("test_table", values2);

        ArrayList<HashMap<String, Object>> orderedRows = dbLoader.getOrderedRows("test_table", "id");
        assertNotNull(orderedRows);
        assertFalse(orderedRows.isEmpty());
        assertEquals(2, orderedRows.get(0).get("ID"));
        assertEquals(1, orderedRows.get(1).get("ID"));
    }

}
