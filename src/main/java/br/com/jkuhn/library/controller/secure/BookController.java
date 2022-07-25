package br.com.jkuhn.library.controller.secure;

import br.com.jkuhn.library.entity.Book;
import br.com.jkuhn.library.services.implementations.BookServiceImpl;
import br.com.jkuhn.library.validator.BookValidator;
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

public class BookController extends SelectorComposer<Component> {
    private static final long serialVersionUID = 1L;

    @WireVariable
    private BookServiceImpl bookServiceImpl;

    @WireVariable
    private BookValidator bookValidator;

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

    @Wire
    private Textbox nameBox;

    @Override
    public void doFinally() throws Exception {
        super.doFinally();

        loadBookList();
        removeButton.setDisabled(true);
    }

    public void loadBookList(){
        bookList = bookServiceImpl.getAllLocalBooks();
        bookListbox.setModel(new ListModelList<Book>(bookList));
    }

    @Listen("onClick = #submitButton")
    public void submitButton() throws Exception {
        String name = nameBox.getValue();

        Book book = new Book(name);
        boolean newBook = selectedBook == null;

        boolean inputValidationOk = true;

        String validName = bookValidator.validateName(book.getName());
        if (validName != null) {
            inputValidationOk = false;
            showError(validName, nameBox);
        }

        // Se a validação falhar não continua
        if (!inputValidationOk) return;

        if (newBook) {
            bookServiceImpl.create(book);
        } else {
            bookServiceImpl.update(selectedBook, book);
        }

        loadBookList();
        showInfo(newBook ? "Cadastro efetuado" : "Cadastro atualizado", nameBox);
        resetButton();
    }

    @Listen("onClick = #resetButton")
    public void resetButton() {
        nameBox.setValue(null);
        ((Selectable<Book>) bookListbox.getModel()).clearSelection();
        selectedBook = null;

        submitButton.setLabel("CRIAR");
        resetButton.setLabel("LIMPAR");
        removeButton.setDisabled(true);
    }

    @Listen("onSelect = #bookListbox")
    public void selectBook() {
        Set<Book> selection = ((Selectable<Book>) bookListbox.getModel()).getSelection();
        if (selection != null && !selection.isEmpty()) {
            Book selected = selection.iterator().next();
            selectedBook = selected;
            nameBox.setValue(selectedBook.getName());

            submitButton.setLabel("EDITAR");
            resetButton.setLabel("CANCELAR");
            removeButton.setDisabled(false);
        }
    }

    @Listen("onClick = #removeButton")
    public void removeButton() {
        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    bookServiceImpl.removeBook(selectedBook);
                    Messagebox.show(String.format("Livro '%s' removido", selectedBook.getName()));

                    resetButton();
                    loadBookList();
                }
            }
        };

        if (selectedBook.getPerson() == null) {
            Messagebox.show(String.format("Confirma a remoção do livro '%s' ?", selectedBook.getName()), "Remover livro", new Messagebox.Button[]{
                    Messagebox.Button.YES, Messagebox.Button.NO}, Messagebox.QUESTION, clickListener);
        } else {
            String msg = String.format("O livro '%s' está reservado para ", selectedBook.getName()) + selectedBook.getPerson().getName() + ", deseja remover mesmo assim ?";
            Messagebox.show(msg, "Remover livro", new Messagebox.Button[]{
                    Messagebox.Button.YES, Messagebox.Button.NO}, Messagebox.QUESTION, clickListener);
        }
    }

    private void showError(String msg, Component ref) {
        Clients.showNotification(msg, "error", ref, "end_center", 4000);
    }

    private void showInfo(String msg, Component ref) {
        Clients.showNotification(msg, "info", ref, "end_center", 4000);
    }
}
