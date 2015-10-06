package edu.wctc.jss.bookapp.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;


public interface DBStrategy {

    void closeConnection() throws SQLException;

    void deleteRecordById(String tableName, String primaryKeyFieldName, Object primaryKeyValue) throws SQLException;

    List<Map<String, Object>> findAllRecords(String tableName) throws SQLException;

    Map<String, Object> findByID(String tableName, String primaryKeyFieldName, Object primaryKeyValue) throws SQLException;

    void insertRecord(String tableName, List columnNames, List values) throws SQLException;

    void openConnection(String driverClass, String url, String userName, String password) throws Exception;
    
    void openConnection(DataSource ds) throws SQLException;

    void updateRecord(String tableName, List columnNames, List values, String primaryKeyFieldName, Object primaryKeyValue) throws SQLException;
    
}
