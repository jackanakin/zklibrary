package br.com.jkuhn.library.servicos.implementacoes;

import br.com.jkuhn.library.entidades.Livros;
import br.com.jkuhn.library.repositorio.LivrosRepositorios;
import br.com.jkuhn.library.servicos.interfaces.LivrosServicosInterfaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivrosServicos implements LivrosServicosInterfaces {
    @Autowired
    private LivrosRepositorios livrosRepositorios;

    @Override
    public List<Livros> findAll() {
        System.out.println("FIND ALL");
        return livrosRepositorios.findAll();
    }

    @Override
    public Livros findByCodigo(int codigo) {
        return null;
    }

    @Override
    public void save(Livros livros) {

    }

    @Override
    public void deleteByCodigo(int codigo) {

    }
}
