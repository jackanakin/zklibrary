package br.com.jkuhn.library.controller.secure;

import br.com.jkuhn.library.entity.Person;
import br.com.jkuhn.library.services.implementations.PersonServiceImpl;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import java.util.ArrayList;
import java.util.List;

public class PersonController extends SelectorComposer<Component> {
    private static final long serialVersionUID = 1L;

    @WireVariable
    private PersonServiceImpl personServiceImpl;

    private List<Person> personList;

    @Wire
    private Listbox personListbox;

    @Wire
    private Textbox nameBox;

    @Wire
    private Textbox emailBox;

    @Wire
    private Textbox passwordBox;

    @Wire
    private Textbox confirmPasswordBox;

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
    public void submitButton(){
        System.out.println("submitButton: PersonController");
        String name = nameBox.getValue();
        String email = emailBox.getValue();
        String password = passwordBox.getValue();
        String confirmPassword = confirmPasswordBox.getValue();

        System.out.println(name);
        System.out.println(email);
        System.out.println(password);
        System.out.println(confirmPassword);

        showNotify("Changed to:sasdasd ", nameBox);

        personList.add(new Person(name, email));
        personListbox.setModel(new ListModelList<Person>(personList));
    }

    @Command
    public void save() {
        System.out.println("saveeeeee");
    }

    private void showNotify(String msg, Component ref) {
        Clients.showNotification(msg, "info", ref, "end_center", 2000);
    }
}
