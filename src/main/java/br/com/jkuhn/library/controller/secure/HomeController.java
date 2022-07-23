package br.com.jkuhn.library.controller.secure;

import br.com.jkuhn.library.entity.Book;
import br.com.jkuhn.library.entity.Person;
import br.com.jkuhn.library.services.implementations.BookServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Listen("onClick = #submitButton")
    public void submitButton() throws Exception {
        //loadBookList();
        showInfo("Reserva", win);
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
            selectedBook = selected;
            reserveBook();
        }
    }

    public void reserveBook() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        System.out.println(authentication);
        System.out.println(authentication.getName());
        System.out.println(authentication.getAuthorities());
        System.out.println(authentication.getDetails());

        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    //personServiceImpl.delete(selectedPerson);


                    Messagebox.show(String.format("Livro %s retirado", selectedBook.getName()));
                    resetButton();
                    //personList = personServiceImpl.findAll();
                    //personListbox.setModel(new ListModelList<Person>(personList));
                }
            }
        };
        Messagebox.show(String.format("Quer retirar o livro ?\n% \nNome: %s", selectedBook.getName()), "Retirar livro", new Messagebox.Button[]{
                Messagebox.Button.YES, Messagebox.Button.NO}, Messagebox.QUESTION, clickListener);
    }

    private void showError(String msg, Component ref) {
        Clients.showNotification(msg, "error", ref, "end_center", 2000);
    }

    private void showInfo(String msg, Component ref) {
        Clients.showNotification(msg, "info", ref, "end_center", 3000);
    }
}
