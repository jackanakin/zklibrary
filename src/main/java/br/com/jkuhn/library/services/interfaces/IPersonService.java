package br.com.jkuhn.library.services.interfaces;


import br.com.jkuhn.library.entity.Person;

import java.util.List;

public interface IPersonService {
    public List<Person> findAll();

    public Person findById(int id);

    public void save(Person person);

    public void deleteById(int id);
}
