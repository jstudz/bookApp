package edu.wctc.jss.bookapp.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AuthorDao implements AuthorDaoStrategy {
    private DBStrategy db;
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    private static final String tableName = "author";
    private static final String primaryKeyName = "author_id";

    public AuthorDao(DBStrategy db, String driverClass, String url, String userName, String password) {
        this.db = db;
        this.driverClass = driverClass;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }
    
    @Override
    public final List<Author> findAllAuthors() throws Exception {
        db.openConnection(driverClass, url, userName, password);
        List<Author> records = new ArrayList<>();
        
        List<Map<String, Object>> allAuthors = db.findAllRecords(tableName);
        for (Map record: allAuthors) {
            Author author = new Author();
            
            Object obj = record.get("author_id");
            author.setAuthorId((Integer)obj);
            
            String name = record.get("author_name").toString();
            author.setAuthorName(name);
            
            obj = record.get("date_created");
            author.setDateCreated((Date) obj);
            
            records.add(author);
        }
        return records;
    }
    
    @Override
    public final Author getAuthorById(Integer authorId) throws Exception {
        db.openConnection(driverClass, url, userName, password);
        
        Map<String, Object> record = db.findByID(tableName, primaryKeyName, authorId);
        Author author = new Author();
        author.setAuthorId((Integer)record.get("author_id"));
        author.setAuthorName(record.get("author_name").toString());
        author.setDateCreated((Date)record.get("date_created"));
        
        return author;
    }
    
    @Override
    public final void deleteAuthor(Author author) throws Exception {
        db.openConnection(driverClass, url, userName, password);
        db.deleteRecordById(tableName, primaryKeyName, author.getAuthorId());
    }
    
    @Override
    public final void addNewAuthor(Author author) throws Exception {
        db.openConnection(driverClass, url, userName, password);
        
        List columnNames = new ArrayList();
        columnNames.add("author_name");
        columnNames.add("date_created");
        
        List columnValues = new ArrayList();
        columnValues.add(author.getAuthorName());
        columnValues.add(author.getDateCreated());
        
        db.insertRecord(tableName, columnNames, columnValues);
    }
    
    @Override
    public final void updateAuthor(Author author) throws Exception {
        db.openConnection(driverClass, url, userName, password);
        
        List columnNames = new ArrayList();
        columnNames.add("author_id");
        columnNames.add("author_name");
        columnNames.add("date_created");
        
        List columnValues = new ArrayList();
        columnValues.add(author.getAuthorId());
        columnValues.add(author.getAuthorName());
        columnValues.add(author.getDateCreated());
        
        db.updateRecord(tableName, columnNames, columnValues, primaryKeyName, author.getAuthorId());
    }
}
