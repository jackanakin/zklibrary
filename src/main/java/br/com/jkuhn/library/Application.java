package br.com.jkuhn.library;

import br.com.jkuhn.library.entity.Book;
import br.com.jkuhn.library.services.implementacoes.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@Controller
public class Application {
    @Autowired
    private BookService bookService;
    public static void main(String[] args) throws Throwable {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/login")
    public String login() {
        System.out.println("AQUI INICIOU O APP");
        List<Book> livros = bookService.findAll();
        System.out.println(livros);

        return "login";
    }

    @GetMapping("/secure/{page}")
    public String secure(@PathVariable String page) {
        return "secure/" + page;
    }
}