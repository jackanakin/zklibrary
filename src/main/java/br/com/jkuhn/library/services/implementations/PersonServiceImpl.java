package br.com.jkuhn.library.services.implementations;

import br.com.jkuhn.library.configuration.UserRoles;
import br.com.jkuhn.library.dao.IAuthoritieDAO;
import br.com.jkuhn.library.dao.IBookDAO;
import br.com.jkuhn.library.dao.IPersonDAO;
import br.com.jkuhn.library.dao.IUserDAO;
import br.com.jkuhn.library.entity.Authoritie;
import br.com.jkuhn.library.entity.Book;
import br.com.jkuhn.library.entity.Person;
import br.com.jkuhn.library.entity.User;
import br.com.jkuhn.library.services.interfaces.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PersonServiceImpl implements IPersonService {

    @Autowired
    private IPersonDAO personDAO;

    @Autowired
    private IBookDAO bookDAO;

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private RestConsumerServiceImpl restConsumerServiceImpl;

    @Autowired
    private IAuthoritieDAO authoritieDAO;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public List<Person> findAll() {
        return personDAO.findAll();
    }

    @Override
    public void update(Person previous, Person next) throws Exception {
        boolean newEmail = !next.getEmail().equals(previous.getUser().getUsername());
        boolean newPassword = next.getPassword().length() > 0;

        // SE MUDOU O E-MAIL
        if (newEmail) {
            User emailInUse = userDAO.findByUsername(next.getEmail());

            if (emailInUse != null) {
                throw new Exception("Este e-mail já está em uso");
            }

            // LIMPAR PERMISSÕES DE ACESSO
            authoritieDAO.deleteAll(authoritieDAO.findByUsername(previous.getUser().getUsername()));

            // SETA NOVO E-MAIL
            previous.getUser().setUsername(next.getEmail());
        }

        // SE MUDOU A SENHA, BCRYPT NA NOVA
        if (newPassword){
            String encodedPassword = userService.encodePassword(next.getPassword());
            previous.getUser().setPassword(encodedPassword);
        }

        previous.setName(next.getName());

        userDAO.save(previous.getUser());
        if (newEmail) {
            authoritieDAO.save(new Authoritie(previous.getUser().getUsername(), UserRoles.USER));
        }
        personDAO.save(previous);
    }

    @Override
    public void create(Person person) throws Exception {
        User user = new User(person.getEmail(), person.getPassword(), true);

        if(userService.checkIfUserExist(user.getUsername())){
            throw new Exception("Este e-mail já está em uso");
        }

        user.setPassword(userService.encodePassword(user.getPassword()));
        User persistedUser = userDAO.save(user);

        authoritieDAO.save(new Authoritie(user.getUsername(), UserRoles.USER));

        person.setUser(persistedUser);
        personDAO.save(person);
    }

    @Override
    @Transactional
    public void delete(Person person) throws Exception {
        // Devolve os livros na API da INTERACT
        List<Book> interactBookList = bookDAO.findAllByCodeIsNotNullAndPersonId(person.getId());
        for (Book book : interactBookList) {
            restConsumerServiceImpl.put(book.getCode(), 0);
        }
        bookDAO.deleteAll(interactBookList);

        authoritieDAO.deleteAll(authoritieDAO.findByUsername(person.getUser().getUsername()));
        bookDAO.removePersonFromBook(person);
        personDAO.delete(person);
    }
}
