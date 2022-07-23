package br.com.jkuhn.library.entity;

import br.com.jkuhn.library.configuration.UserRoles;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class Authoritie {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String username;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRoles authority;

    public Authoritie() {
    }

    public Authoritie(String username, UserRoles authority) {
        this.username = username;
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRoles getAuthority() {
        return authority;
    }

    public void setAuthority(UserRoles authority) {
        this.authority = authority;
    }
}
