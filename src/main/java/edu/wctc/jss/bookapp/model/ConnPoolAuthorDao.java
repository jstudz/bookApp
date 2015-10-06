package edu.wctc.jss.bookapp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;


public class ConnPoolAuthorDao implements AuthorDaoStrategy {

    private DBStrategy db;
    private DataSource ds;
    private static final String tableName = "author";
    private static final String primaryKeyName = "author_id";

    public ConnPoolAuthorDao(DBStrategy db, DataSource ds) {
        this.db = db;
        this.ds = ds;
    }

    @Override
    public void addNewAuthor(Author author) throws Exception {
        db.openConnection(ds);

        List columnNames = new ArrayList();
        columnNames.add("author_name");
        columnNames.add("date_created");

        List columnValues = new ArrayList();
        columnValues.add(author.getAuthorName());
        columnValues.add(author.getDateCreated());

        db.insertRecord(tableName, columnNames, columnValues);
    }

    @Override
    public void deleteAuthor(Author author) throws Exception {
        db.openConnection(ds);
        db.deleteRecordById(tableName, primaryKeyName, author.getAuthorId());
    }

    @Override
    public List<Author> findAllAuthors() throws Exception {
        db.openConnection(ds);
        List<Author> records = new ArrayList<>();

        List<Map<String, Object>> allAuthors = db.findAllRecords(tableName);
        for (Map record : allAuthors) {
            Author author = new Author();

            Object obj = record.get("author_id");
            author.setAuthorId((Integer) obj);

            String name = record.get("author_name").toString();
            author.setAuthorName(name);

            obj = record.get("date_created");
            author.setDateCreated((Date) obj);

            records.add(author);
        }
        return records;
    }

    @Override
    public Author getAuthorById(Integer authorId) throws Exception {
        db.openConnection(ds);

        Map<String, Object> record = db.findByID(tableName, primaryKeyName, authorId);
        Author author = new Author();
        author.setAuthorId((Integer) record.get("author_id"));
        author.setAuthorName(record.get("author_name").toString());
        author.setDateCreated((Date) record.get("date_created"));

        return author;
    }

    @Override
    public void updateAuthor(Author author) throws Exception {
        db.openConnection(ds);

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
