package br.com.jkuhn.library.dao;

import br.com.jkuhn.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDAO extends JpaRepository<User, Integer> {
    public User findByUsername(String username);
}
