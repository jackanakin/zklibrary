package br.com.jkuhn.library.dao;

import br.com.jkuhn.library.entity.Book;
import br.com.jkuhn.library.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IBookDAO extends JpaRepository<Book, Long> {
    public Book findByName(String name);

    public List<Book> findAllByCodeIsNull();

    public List<Book> findAllByCodeIsNotNullAndPersonId(Long personId);

    public List<Book> findAllByPersonId(Long personId);

    @Modifying
    @Query("UPDATE Book b SET b.person = NULL, b.booked = 0 WHERE b.person = :person")
    public void removePersonFromBook(@Param("person") Person person);

}
