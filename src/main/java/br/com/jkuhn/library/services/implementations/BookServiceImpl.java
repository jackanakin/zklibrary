package br.com.jkuhn.library.services.implementations;

import br.com.jkuhn.library.dao.IBookDAO;
import br.com.jkuhn.library.entity.Book;
import br.com.jkuhn.library.services.interfaces.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements IBookService {
    @Autowired
    private IBookDAO bookDAO;

    @Override
    public List<Book> findAll() {
        return bookDAO.findAll();
    }

    @Override
    public Book findById(int id) {
        return null;
    }

    @Override
    public void save(Book books) {

    }

    @Override
    public void deleteById(int id) {

    }
}
