package br.com.jkuhn.library.entidades;

import javax.persistence.*;

@Entity
@Table(name="livros")
public class Livros {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private int codigo;

    @Column
    private String nome;

    @Column
    private String autor;

    @Column
    private String descricao;

    public Livros(){

    }

    public Livros(String nome, String autor, String descricao) {
        this.nome = nome;
        this.autor = autor;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
