package br.com.jkuhn.library.rest;

import br.com.jkuhn.library.entity.Book;
import br.com.jkuhn.library.services.implementations.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rs/books")
public class BooksREST {
    @Autowired
    private BookServiceImpl bookServiceImpl;

    @GetMapping("")
    public List<Book> get() {
        return bookServiceImpl.getAllLocalBooks();
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> get(@PathVariable Long bookId) {
        Book book = bookServiceImpl.getLocalBook(bookId);

        if (book == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(book, HttpStatus.OK);
        }
    }
}
