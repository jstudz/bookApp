
package edu.wctc.jss.bookapp.model;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;


public class MySqlDb implements DBStrategy {
    private Connection conn;
 
    @Override
    public void openConnection(String driverClass, String url, String userName, String password) throws Exception {
        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }
    
    @Override
    public void openConnection(DataSource ds) throws SQLException {
        conn = ds.getConnection();
    }
    
    @Override
    public void closeConnection() throws SQLException {
        conn.close();
    }
    
    @Override
    public final List<Map<String, Object>> findAllRecords(String tableName) throws SQLException {
        String sql = "SELECT * FROM " + tableName;
        final List<Map<String, Object>> recordList = new ArrayList<>();
        
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        
        while (rs.next()) {
            Map<String, Object> record = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                record.put(metaData.getColumnName(i), rs.getObject(i));
            }
            recordList.add(record);
        }
        return recordList;
    }
    
    @Override
    public final Map<String,Object> findByID (String tableName, String primaryKeyFieldName, Object primaryKeyValue) throws SQLException {
        String sql = "SELECT * FROM " + tableName + " WHERE " + primaryKeyFieldName + " = ?";
        PreparedStatement stmt = null;
        final Map<String, Object> record = new HashMap<>();
        
        stmt = conn.prepareStatement(sql);
        stmt.setObject(1, primaryKeyValue);
        ResultSet rs = stmt.executeQuery();
        final ResultSetMetaData metaData = rs.getMetaData();
        final int fields = metaData.getColumnCount();
        
        if (rs.next()) {
            for (int i = 1; i <= fields; i++) {
                record.put(metaData.getColumnName(i), rs.getObject(i));
            }
        }
        return record;
    }
    
    @Override
    public void insertRecord(String tableName, List columnNames, List values) throws SQLException {
        StringBuffer sql = new StringBuffer("INSERT into " + tableName + " (");
        
        final Iterator i = columnNames.iterator();
        while (i.hasNext()) {
            sql.append((String) i.next()).append(", ");
        }
        
        sql = new StringBuffer(sql.toString().substring(0, sql.lastIndexOf(",")) + ") VALUES (");
        
        for (int j = 0; j < columnNames.size(); j++) {
            sql.append("?, ");
        }
        
        final String finalSqlStmt = sql.toString().substring(0, sql.lastIndexOf(",")) + ")";
        PreparedStatement stmt = conn.prepareStatement(finalSqlStmt);
        
        final Iterator k = values.iterator();
        int index = 1;
        while (k.hasNext()) {
            final Object obj = k.next();
            stmt.setObject(index++, obj);
        }
        
        stmt.executeUpdate();
    }
    
    @Override
    public void updateRecord (String tableName, List columnNames, List values, String primaryKeyFieldName, Object primaryKeyValue) throws SQLException {
        StringBuffer sql = new StringBuffer("UPDATE " + tableName + " SET ");
        final Iterator i = columnNames.iterator();
        while(i.hasNext()) {
            sql.append((String) i.next()).append("= ?, ");
        }
        
        sql = new StringBuffer(sql.toString().substring(0, sql.lastIndexOf(",")));
        sql.append(" WHERE ").append(primaryKeyFieldName).append(" = ?");
        
        final String finalSqlStmt = sql.toString();
        PreparedStatement stmt = conn.prepareStatement(finalSqlStmt);
        
        final Iterator j = values.iterator();
        int index = 1;
        while (j.hasNext()) {
            final Object obj = j.next();
            stmt.setObject(index++, obj);
        }
        
        stmt.setObject(index++, primaryKeyValue);
        stmt.executeUpdate();
        
    }
    
    @Override
    public void deleteRecordById(String tableName, String primaryKeyFieldName, Object primaryKeyValue) throws SQLException {
        String sql = "DELETE FROM " +  tableName + " WHERE " + primaryKeyFieldName + " = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setObject(1, primaryKeyValue);
        stmt.executeUpdate();
    }
}
