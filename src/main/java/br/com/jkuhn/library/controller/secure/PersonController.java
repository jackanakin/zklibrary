package br.com.jkuhn.library.controller.secure;

import br.com.jkuhn.library.entity.Person;
import br.com.jkuhn.library.services.implementations.PersonServiceImpl;
import br.com.jkuhn.library.validator.PersonValidator;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import java.util.ArrayList;
import java.util.List;

public class PersonController extends SelectorComposer<Component> {
    private static final long serialVersionUID = 1L;

    @WireVariable
    private PersonServiceImpl personServiceImpl;

    @WireVariable
    private PersonValidator personValidator;

    private List<Person> personList;

    @Wire
    private Window win;

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
        System.out.println("Do finally: PersonController");
        //List<Person> books = personServiceImpl.findAll();
        Person person = new Person("Joao", "@joao");
        Person person2 = new Person("Joao2", "@joao2");
        personList = new ArrayList<>();
        personList.add(person2);personList.add(person);
        personListbox.setModel(new ListModelList<Person>(personList));

        System.out.println(personList);
    }

    @Listen("onClick = #submitButton")
    public void submitButton() throws Exception {
        String name = nameBox.getValue();
        String email = emailBox.getValue();
        String password = passwordBox.getValue();

        Person p = new Person(name, email, password);
        boolean valid = true;

        String validName = personValidator.validateName(p.getName());
        if (validName != null) {
            valid = false;
            showError(validName, nameBox);
        }

        String validEmail = personValidator.validateEmail(p.getEmail());
        if (validEmail != null) {
            valid = false;
            showError(validEmail, emailBox);
        }

        String validPassword = personValidator.validatePassword(p.getPassword());
        if (validPassword != null) {
            valid = false;
            showError(validPassword, passwordBox);
        }

        // Se validação falhar não continua
        if (!valid) return;

        System.out.println("personServiceImpl indo");
        personServiceImpl.save(p);
        System.out.println("personServiceImpl voltou");


        personList.add(new Person(p.getName(), p.getEmail()));
        personListbox.setModel(new ListModelList<Person>(personList));
        showInfo("Pessoa cadastrada", win);
        resetButton();
    }

    @Listen("onClick = #resetButton")
    public void resetButton() {
        System.out.println("resetButton");
        nameBox.setValue(null);
        emailBox.setValue(null);
        passwordBox.setValue(null);
    }

    private void showError(String msg, Component ref) {
        Clients.showNotification(msg, "error", ref, "end_center", 2000);
    }

    private void showInfo(String msg, Component ref) {
        Clients.showNotification(msg, "info", ref, "end_center", 2000);
    }
}
