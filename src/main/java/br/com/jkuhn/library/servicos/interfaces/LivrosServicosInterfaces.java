package br.com.jkuhn.library.servicos.interfaces;

import br.com.jkuhn.library.entidades.Livros;

import java.util.List;

public interface LivrosServicosInterfaces {
    public List<Livros> findAll();

    public Livros findByCodigo(int codigo);

    public void save(Livros livros);

    public void deleteByCodigo(int codigo);
}
