package br.com.jkuhn.library.dao;

import br.com.jkuhn.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBookDAO extends JpaRepository<Book, Long> {
    public Book findByName(String name);

    public List<Book> findAllByPersonId(Long personId);
}
