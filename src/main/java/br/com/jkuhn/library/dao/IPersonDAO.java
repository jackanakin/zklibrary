package br.com.jkuhn.library.dao;

import br.com.jkuhn.library.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonDAO extends JpaRepository<Person, Integer> {
}
