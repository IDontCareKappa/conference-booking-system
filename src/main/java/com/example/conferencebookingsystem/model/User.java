package com.example.conferencebookingsystem.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String login;

    @NotNull
    private String email;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private Set<Lecture> lectures = new HashSet<>();

    public User() {
    }

    public User(String login, String email) {
        this.login = login;
        this.email = email;
    }
}
