package br.com.jkuhn.library.services.interfaces;


import br.com.jkuhn.library.entity.Book;

import java.util.List;

public interface IBookService {
    public List<Book> findAll();

    public void create(Book book) throws Exception;

    public void update(Book previous, Book next) throws Exception;

    public void reserve(Book previous) throws Exception;
}
