package br.com.jkuhn.library.controller.secure;

import br.com.jkuhn.library.entity.Book;
import br.com.jkuhn.library.services.implementations.BookServiceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.*;
import org.zkoss.zul.ext.Selectable;

import java.util.List;
import java.util.Set;

public class HomeController extends SelectorComposer<Component> {
    private static final long serialVersionUID = 1L;

    @WireVariable
    private BookServiceImpl bookServiceImpl;

    private List<Book> bookList;

    private Book selectedBook = null;

    @Wire
    private Window win;

    @Wire
    private Button submitButton;

    @Wire
    private Button resetButton;

    @Wire
    private Button removeButton;

    @Wire
    private Listbox bookListbox;

    @Override
    public void doFinally() throws Exception {
        super.doFinally();

        loadBookList();
    }

    public void loadBookList(){
        bookList = bookServiceImpl.findAll();
        bookListbox.setModel(new ListModelList<Book>(bookList));
    }

    @Listen("onClick = #resetButton")
    public void resetButton() {
        ((Selectable<Book>) bookListbox.getModel()).clearSelection();
        selectedBook = null;
    }

    @Listen("onSelect = #bookListbox")
    public void selectBook() {
        Set<Book> selection = ((Selectable<Book>) bookListbox.getModel()).getSelection();
        if (selection != null && !selection.isEmpty()) {
            Book selected = selection.iterator().next();
            if (selected.getPerson() != null){
                ((Selectable<Book>) bookListbox.getModel()).clearSelection();
                String msg = String.format("O livro '%s' já foi retirar", selected.getName());
                showWarning(msg, bookListbox);
            } else {
                selectedBook = selected;
                reserveBook();
            }
        }
    }

    public void reserveBook() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    bookServiceImpl.reserve(selectedBook, username);

                    Messagebox.show(String.format("Livro %s retirado", selectedBook.getName()));
                    resetButton();
                    bookList = bookServiceImpl.findAll();
                    bookListbox.setModel(new ListModelList<Book>(bookList));
                }
            }
        };

        Messagebox.show(String.format("Quer retirar o livro ? \nNome: %s", selectedBook.getName()), "Retirar livro", new Messagebox.Button[]{
                Messagebox.Button.YES, Messagebox.Button.NO}, Messagebox.QUESTION, clickListener);
    }

    private void showWarning(String msg, Component ref) {
        Clients.showNotification(msg, "warning", ref, "middle_center", 2000);
    }

    private void showError(String msg, Component ref) {
        Clients.showNotification(msg, "error", ref, "end_center", 2000);
    }

    private void showInfo(String msg, Component ref) {
        Clients.showNotification(msg, "info", ref, "end_center", 3000);
    }
}
