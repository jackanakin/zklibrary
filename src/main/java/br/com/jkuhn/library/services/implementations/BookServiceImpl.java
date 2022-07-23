package br.com.jkuhn.library.services.implementations;

import br.com.jkuhn.library.dao.IBookDAO;
import br.com.jkuhn.library.dao.IPersonDAO;
import br.com.jkuhn.library.dao.IUserDAO;
import br.com.jkuhn.library.entity.Book;
import br.com.jkuhn.library.entity.Person;
import br.com.jkuhn.library.services.interfaces.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements IBookService {
    @Autowired
    private IBookDAO bookDAO;

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private IPersonDAO personDAO;

    @Override
    public List<Book> findAllReservedByUsername(String username) {
        Person person = personDAO.findByUserUsername(username);
        return bookDAO.findAllByPersonId(person.getId());
    }

    @Override
    public List<Book> findAll() {
        return bookDAO.findAll();
    }

    @Override
    public void create(Book book) throws Exception {
        Book nameInUse = bookDAO.findByName(book.getName());
        if (nameInUse != null) {
            throw new Exception("Este livro já foi adicionado");
        }

        bookDAO.save(book);
    }

    @Override
    public void update(Book previous, Book next) throws Exception {
        Book nameInUse = bookDAO.findByName(next.getName());

        if (nameInUse != null && !previous.getName().equals(next.getName())) {
            throw new Exception("Este nome já foi usado");
        }

        previous.setName(next.getName());
        bookDAO.save(previous);
    }

    @Override
    public void reserveBook(Book book, String username) throws Exception {
        Person person = personDAO.findByUserUsername(username);
        book.setPerson(person);

        Book bookReserved = bookDAO.findById(book.getId()).orElseThrow(() -> new Exception("Livro não encontrado, verifique se não foi removido do acervo"));
        if (bookReserved.getPerson() != null) throw new Exception("Este livro acabou de ser reservado por outra pessoa");

        bookDAO.save(book);
    }

    @Override
    public void returnBook(Book book) {
        book.setPerson(null);
        bookDAO.save(book);
    }

}
