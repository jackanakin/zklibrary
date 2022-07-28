package br.com.jkuhn.library.rest;

import br.com.jkuhn.library.dto.BookDTO;
import br.com.jkuhn.library.entity.Book;
import br.com.jkuhn.library.services.implementations.BookServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rs/books")
public class BooksREST {
    @Autowired
    private BookServiceImpl bookServiceImpl;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<List<BookDTO>> get() {
        List<Book> bookList = bookServiceImpl.getAllLocalBooks();
        List<BookDTO> bookDTOList = bookList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(bookDTOList, HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO> get(@PathVariable Long bookId) {
        Book book = bookServiceImpl.getLocalBook(bookId);

        if (book == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(convertToDto(book), HttpStatus.OK);
        }
    }

    private BookDTO convertToDto(Book book) {
        BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
        return bookDTO;
    }
}
