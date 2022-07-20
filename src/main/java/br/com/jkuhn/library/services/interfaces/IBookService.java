package br.com.jkuhn.library.services.interfaces;


import br.com.jkuhn.library.entity.Book;

import java.util.List;

public interface IBookService {
    public List<Book> findAll();

    public Book findById(int id);

    public void save(Book books);

    public void deleteById(int id);
}
