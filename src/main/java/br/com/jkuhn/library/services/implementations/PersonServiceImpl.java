package br.com.jkuhn.library.services.implementations;

import br.com.jkuhn.library.configuration.UserRoles;
import br.com.jkuhn.library.dao.IAuthoritieDAO;
import br.com.jkuhn.library.dao.IPersonDAO;
import br.com.jkuhn.library.dao.IUserDAO;
import br.com.jkuhn.library.entity.Authoritie;
import br.com.jkuhn.library.entity.Person;
import br.com.jkuhn.library.entity.User;
import br.com.jkuhn.library.services.interfaces.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements IPersonService {

    @Autowired
    private IPersonDAO personDAO;

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private IAuthoritieDAO authoritieDAO;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public List<Person> findAll() {
        return personDAO.findAll();
    }

    @Override
    public Person findById(int id) {
        return null;
    }

    @Override
    public void update(Person previous, Person next) throws Exception {
        boolean newEmail = !next.getEmail().equals(previous.getUser().getUsername());

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
        if (next.getPassword().length() > 0){
            previous.setPassword(next.getPassword());
            userService.encodePassword(previous.getUser());
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

        userService.encodePassword(user);
        User persistedUser = userDAO.save(user);

        authoritieDAO.save(new Authoritie(user.getUsername(), UserRoles.USER));

        person.setUser(persistedUser);
        personDAO.save(person);
    }

    @Override
    public void delete(Person person) {
        authoritieDAO.deleteAll(authoritieDAO.findByUsername(person.getUser().getUsername()));
        personDAO.delete(person);
    }
}
