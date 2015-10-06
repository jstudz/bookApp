package edu.wctc.jss.bookapp.model;

import java.util.List;


public class AuthorService {
    private AuthorDaoStrategy dao;

    public AuthorService(AuthorDaoStrategy dao) {
        this.dao = dao;
    }
    
    public final List<Author> getAllAuthors() throws Exception {
        return dao.findAllAuthors();
    }
    
    public final Author getAuthorById(String authorId) throws Exception {
        return dao.getAuthorById(Integer.parseInt(authorId));
    }
    
    public final void deleteAuthor(Author author) throws Exception {
        dao.deleteAuthor(author);
    }
    
    public final void addNewAuthor(Author author) throws Exception {
        dao.addNewAuthor(author);
    }
    
    public final void updateAuthor(Author author) throws Exception{
        dao.updateAuthor(author);
    }
}
