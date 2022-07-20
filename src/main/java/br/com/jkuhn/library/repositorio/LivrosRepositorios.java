package br.com.jkuhn.library.repositorio;

import br.com.jkuhn.library.entidades.Livros;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivrosRepositorios extends JpaRepository<Livros, Integer> {
}
