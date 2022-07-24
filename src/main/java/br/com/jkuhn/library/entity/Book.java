package br.com.jkuhn.library.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String code;

    @Column
    private Integer booked = 0; //0=Available / 1=Reserved

    @ManyToOne
    @JoinColumn(name="booked_person_id")
    private Person person;

    public Book() {
    }

    public Book(String name, String code, Integer booked) {
        this.name = name;
        this.code = code;
        this.booked = booked;
    }

    public Book(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getBooked() {
        return booked;
    }

    public void setBooked(Integer booked) {
        this.booked = booked;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
