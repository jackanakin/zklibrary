package br.com.jkuhn.library.controller.secure;

import br.com.jkuhn.library.entity.Book;
import br.com.jkuhn.library.services.implementations.BookServiceImpl;
import br.com.jkuhn.library.services.implementations.RestConsumerServiceImpl;
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

    @WireVariable
    private RestConsumerServiceImpl restConsumerServiceImpl;

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
        bookList = bookServiceImpl.getAllLocalBooks();

        try{
            List<Book> apiBookList = restConsumerServiceImpl.get();
            bookList.addAll(apiBookList);
        } catch (Exception e){
            Messagebox.show("Biblioteca remota indisponível no momento!");
        }

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
            if (selected.getBooked() == 1){
                ((Selectable<Book>) bookListbox.getModel()).clearSelection();
                String msg = String.format("O livro '%s' já foi retirado", selected.getName());
                Clients.showNotification(msg, "warning", bookListbox, "middle_center", 2000);
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
                    selectedBook.setBooked(1);

                    bookServiceImpl.reserveBook(selectedBook, username);

                    Messagebox.show(String.format("Livro '%s' retirado", selectedBook.getName()));
                    resetSelection();
                    loadBookList();
                }
            }
        };

        Messagebox.show(String.format("Deseja retirar o livro '%s' ?", selectedBook.getName()), "Retirar livro", new Messagebox.Button[]{
                Messagebox.Button.YES, Messagebox.Button.NO}, Messagebox.QUESTION, clickListener);
    }

    private void showError(String msg, Component ref) {
        Clients.showNotification(msg, "error", ref, "end_center", 2000);
    }

    private void showInfo(String msg, Component ref) {
        Clients.showNotification(msg, "info", ref, "end_center", 3000);
    }
}
