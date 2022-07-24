package br.com.jkuhn.library.services.interfaces;


import br.com.jkuhn.library.entity.Book;

import java.util.List;

public interface IBookService {

    public List<Book> getAllLocalBooks();

    public List<Book> findAllReservedByUsername(String username);

    public void create(Book book) throws Exception;

    public void update(Book previous, Book next) throws Exception;

    public void reserveBook(Book book,  String username) throws Exception;

    public void returnBook(Book book);

}
