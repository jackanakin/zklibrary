package br.com.jkuhn.library.dao;

import br.com.jkuhn.library.entity.Authoritie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthoritieDAO extends JpaRepository<Authoritie, Integer> {
    public Authoritie findByUsername(String username);
}
