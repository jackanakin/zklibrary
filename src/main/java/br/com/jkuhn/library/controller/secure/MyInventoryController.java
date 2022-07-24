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

public class MyInventoryController extends SelectorComposer<Component> {
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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        bookList = bookServiceImpl.findAllReservedByUsername(username);
        bookListbox.setModel(new ListModelList<Book>(bookList));
    }

    public void resetSelection() {
        ((Selectable<Book>) bookListbox.getModel()).clearSelection();
        selectedBook = null;
    }

    @Listen("onSelect = #bookListbox")
    public void selectBook() {
        Set<Book> selection = ((Selectable<Book>) bookListbox.getModel()).getSelection();
        if (selection != null && !selection.isEmpty()) {
            Book selected = selection.iterator().next();

            selectedBook = selected;
            returnBook();
        }
    }

    public void returnBook() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    selectedBook.setBooked(0);
                    bookServiceImpl.returnBook(selectedBook);

                    Messagebox.show(String.format("Livro %s devolvido", selectedBook.getName()));
                    resetSelection();
                    bookList = bookServiceImpl.findAllReservedByUsername(username);
                    bookListbox.setModel(new ListModelList<Book>(bookList));
                }
            }
        };

        Messagebox.show(String.format("Deseja devolver o livro ? \nNome: %s", selectedBook.getName()), "Devolver livro", new Messagebox.Button[]{
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
