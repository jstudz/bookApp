package edu.wctc.jss.bookapp.model;

import java.util.Date;


public class Author {
    private String authorName;
    private int authorId;
    private Date dateCreated;

    public Author() {
    }

    public Author(String authorName, Date dateCreated) {
        this.authorName = authorName;
        this.dateCreated = dateCreated;
    }

    public Author(String authorName, int authorId, Date dateCreated) {
        this.authorName = authorName;
        this.authorId = authorId;
        this.dateCreated = dateCreated;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    
}
