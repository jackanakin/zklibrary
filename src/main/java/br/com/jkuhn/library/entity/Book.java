package br.com.jkuhn.library.entity;

import javax.persistence.*;

@Entity
@Table
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String name;

    @Column
    private String author;

    @Column
    private String description;

    public Book() {
    }

    public Book(String name, String author, String description) {
        this.name = name;
        this.author = author;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
