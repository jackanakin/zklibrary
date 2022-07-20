package br.com.jkuhn.library.rest;

import br.com.jkuhn.library.entity.Book;
import br.com.jkuhn.library.services.implementacoes.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rs/books")
public class BooksREST {
    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public List<Book> getBooks() {
        return bookService.findAll();
    }

}
