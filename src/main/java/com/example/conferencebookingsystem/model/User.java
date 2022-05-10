package com.example.conferencebookingsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String login;

    @NotNull
    @Size(max = 255)
    @Email
    private String email;

    @JsonIgnore
    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private Set<Lecture> lectures = new HashSet<>();

    public User() {
    }

    public User(String login, String email) {
        this.login = login;
        this.email = email;
    }
}
