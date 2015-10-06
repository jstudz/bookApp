
package edu.wctc.jss.bookapp.model;

import java.util.List;

public interface AuthorDaoStrategy {

    void addNewAuthor(Author author) throws Exception;

    void deleteAuthor(Author author) throws Exception;

    List<Author> findAllAuthors() throws Exception;

    Author getAuthorById(Integer authorId) throws Exception;

    void updateAuthor(Author author) throws Exception;
    
}
