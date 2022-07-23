package br.com.jkuhn.library.dao;

import br.com.jkuhn.library.entity.Authoritie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAuthoritieDAO extends JpaRepository<Authoritie, String> {

    public List<Authoritie> findByUsername(String username);

    public void deleteByUsername(String username);
}
