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

    @Autowired
    private RestConsumerServiceImpl restConsumerServiceImpl;

    @Override
    public List<Book> getAllReservedByUsername(String username) {
        Person person = personDAO.findByUserUsername(username);
        return bookDAO.findAllByPersonId(person.getId());
    }

    @Override
    public Book getLocalBook(Long id) {
        return bookDAO.findById(id).orElse(null);
    }

    @Override
    public List<Book> getAllLocalBooks() {
        return bookDAO.findAllByCodeIsNull();
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
        if (person == null) throw new Exception("Verifique se o seu cadastro está ativo!");

        if (book.getCode() != null){
            //SE FOR LIVRO DA API
            restConsumerServiceImpl.put(book.getCode(), 1);
        } else {
            //SE FOR LIVRO LOCAL
            Book bookReserved = bookDAO.findById(book.getId()).orElseThrow(() -> new Exception("Livro não encontrado, verifique se não foi removido do acervo"));
            if (bookReserved.getPerson() != null) throw new Exception("Este livro acabou de ser reservado por outra pessoa");
        }

        book.setPerson(person);
        book.setBooked(1);
        bookDAO.save(book);
    }

    @Override
    public void returnBook(Book book) throws Exception {
        book.setPerson(null);

        if (book.getCode() != null){
            //SE FOR LIVRO DA API
            boolean success = false;
            try{
                success = restConsumerServiceImpl.put(book.getCode(), 0);
                bookDAO.delete(book);
            } catch (Exception e){
                throw new Exception("Biblioteca remota indisponível no momento! Tente novamente mais tarde");
            }

            if (success) bookDAO.delete(book);
        } else {
            //SE FOR LIVRO LOCAL
            bookDAO.save(book);
        }
    }

    @Override
    public void removeBook(Book book) {
        bookDAO.delete(book);
    }

}
