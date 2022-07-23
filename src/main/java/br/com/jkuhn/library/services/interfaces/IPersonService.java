package br.com.jkuhn.library.services.interfaces;


import br.com.jkuhn.library.entity.Person;

import java.util.List;

public interface IPersonService {
    public List<Person> findAll();

    public Person findById(int id);

    public void create(Person person) throws Exception;

    public void update(Person previous, Person next) throws Exception;

    public void delete(Person person);
}
