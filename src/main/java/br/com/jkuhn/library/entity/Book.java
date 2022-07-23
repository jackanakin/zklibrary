package br.com.jkuhn.library.entity;

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
    private String remote_code;

    @Column
    private Integer remote_booked;

    @ManyToOne
    @JoinColumn(name="booked_person_id")
    private Person person;

    public Book() {
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

    public String getRemote_code() {
        return remote_code;
    }

    public void setRemote_code(String remote_code) {
        this.remote_code = remote_code;
    }

    public Integer getRemote_booked() {
        return remote_booked;
    }

    public void setRemote_booked(Integer remote_booked) {
        this.remote_booked = remote_booked;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
