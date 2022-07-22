package br.com.jkuhn.library.services.implementations;

import br.com.jkuhn.library.dao.IPersonDAO;
import br.com.jkuhn.library.entity.Person;
import br.com.jkuhn.library.services.interfaces.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements IPersonService {

    @Autowired
    private IPersonDAO personDAO;

    @Override
    public List<Person> findAll() {
        return personDAO.findAll();
    }

    @Override
    public Person findById(int id) {
        return null;
    }

    @Override
    public void save(Person person) {

    }

    @Override
    public void deleteById(int id) {

    }
}
