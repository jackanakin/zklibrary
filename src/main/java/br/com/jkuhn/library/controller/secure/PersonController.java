package br.com.jkuhn.library.controller.secure;

import br.com.jkuhn.library.entity.Book;
import br.com.jkuhn.library.entity.Person;
import br.com.jkuhn.library.services.implementations.BookServiceImpl;
import br.com.jkuhn.library.services.implementations.PersonServiceImpl;
import br.com.jkuhn.library.validator.PersonValidator;
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

public class PersonController extends SelectorComposer<Component> {
    private static final long serialVersionUID = 1L;

    @WireVariable
    private PersonServiceImpl personServiceImpl;

    @WireVariable
    private BookServiceImpl bookServiceImpl;

    @WireVariable
    private PersonValidator personValidator;

    private List<Person> personList;

    private Person selectedPerson = null;

    @Wire
    private Window win;

    @Wire
    private Button submitButton;

    @Wire
    private Button resetButton;

    @Wire
    private Button removeButton;

    @Wire
    private Listbox personListbox;

    @Wire
    private Textbox nameBox;

    @Wire
    private Textbox emailBox;

    @Wire
    private Textbox passwordBox;

    @Override
    public void doFinally() throws Exception {
        super.doFinally();
        personList = personServiceImpl.findAll();
        personListbox.setModel(new ListModelList<Person>(personList));
        removeButton.setDisabled(true);
    }

    @Listen("onClick = #submitButton")
    public void submitButton() throws Exception {
        String name = nameBox.getValue();
        String email = emailBox.getValue();
        String password = passwordBox.getValue();

        Person person = new Person(name, email, password);
        boolean newPerson = selectedPerson == null;

        boolean inputValidationOk = true;

        String validName = personValidator.validateName(person.getName());
        if (validName != null) {
            inputValidationOk = false;
            showError(validName, nameBox);
        }

        String validEmail = personValidator.validateEmail(person.getEmail());
        if (validEmail != null) {
            inputValidationOk = false;
            showError(validEmail, emailBox);
        }

        String validPassword = personValidator.validatePassword(person.getPassword());
        if ((!newPerson && person.getPassword().length() > 0 && validPassword != null) || (newPerson && validPassword != null)) {
            inputValidationOk = false;
            showError(validPassword, passwordBox);
        }

        // Se a validação falhar não continua
        if (!inputValidationOk) return;

        if (newPerson) {
            personServiceImpl.create(person);
        } else {
            personServiceImpl.update(selectedPerson, person);
        }

        personList = personServiceImpl.findAll();
        personListbox.setModel(new ListModelList<Person>(personList));
        showInfo(newPerson ? "Cadastro efetuado" : "Cadastro atualizado", win);
        resetButton();
    }

    @Listen("onClick = #removeButton")
    public void removeButton() {
        List<Book> bookList = bookServiceImpl.getAllReservedByUsername(selectedPerson.getUser().getUsername());

        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    personServiceImpl.delete(selectedPerson);
                    Messagebox.show(String.format("Removido: %s", selectedPerson.getName()));
                    resetButton();
                    personList = personServiceImpl.findAll();
                    personListbox.setModel(new ListModelList<Person>(personList));
                }
            }
        };

        final StringBuilder msg = new StringBuilder(String.format("Confirma a remoção de %s ?\n", selectedPerson.getName()));

        if (bookList.size() > 0){
            msg.append("\n").append("Os seguintes livros reservados serão devolvidos:\n");
            bookList.forEach(book -> msg.append(book.getName()).append("\n"));
        }

        Messagebox.show(msg.toString(), "Remover pessoa", new Messagebox.Button[]{
                Messagebox.Button.YES, Messagebox.Button.NO}, Messagebox.EXCLAMATION, clickListener);
    }

    @Listen("onClick = #resetButton")
    public void resetButton() {
        nameBox.setValue(null);
        emailBox.setValue(null);
        passwordBox.setValue(null);
        ((Selectable<Person>) personListbox.getModel()).clearSelection();
        selectedPerson = null;

        submitButton.setLabel("CRIAR");
        resetButton.setLabel("LIMPAR");
        removeButton.setDisabled(true);
    }

    @Listen("onSelect = #personListbox")
    public void selectPerson() {
        Set<Person> selection = ((Selectable<Person>) personListbox.getModel()).getSelection();
        if (selection != null && !selection.isEmpty()) {
            Person selected = selection.iterator().next();
            selectedPerson = selected;
            nameBox.setValue(selectedPerson.getName());
            emailBox.setValue(selectedPerson.getUser().getUsername());
            passwordBox.setValue(null);

            submitButton.setLabel("EDITAR");
            resetButton.setLabel("CANCELAR");
            removeButton.setDisabled(false);
        }
    }

    private void showError(String msg, Component ref) {
        Clients.showNotification(msg, "error", ref, "end_center", 2000);
    }

    private void showInfo(String msg, Component ref) {
        Clients.showNotification(msg, "info", ref, "end_center", 3000);
    }
}
