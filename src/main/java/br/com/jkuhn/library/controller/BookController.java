package br.com.jkuhn.library.controller;

import br.com.jkuhn.library.entity.Book;
import br.com.jkuhn.library.entity.Car;
import br.com.jkuhn.library.services.implementations.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import java.util.List;

public class BookController extends SelectorComposer<Component> {
    private static final long serialVersionUID = 1L;

    @WireVariable
    private BookServiceImpl bookServiceImpl;

    @Wire
    private Listbox bookListbox;

    @Override
    public void doFinally() throws Exception {
        super.doFinally();
        System.out.println("Do finally");
        List<Book> books = bookServiceImpl.findAll();
        System.out.println(books);

        bookListbox.setModel(new ListModelList<Book>(books));
    }
}
