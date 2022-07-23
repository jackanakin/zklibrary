package br.com.jkuhn.library.dao;

import br.com.jkuhn.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookDAO extends JpaRepository<Book, Integer> {
    public Book findByName(String name);
}
