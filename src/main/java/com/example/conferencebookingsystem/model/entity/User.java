package com.example.conferencebookingsystem.model.entity;

import com.example.conferencebookingsystem.model.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
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
    @Column(unique = true)
    private String login;

    @NotNull
    @Size(max = 255)
    @Email
    @Column(unique = true)
    private String email;

    @JsonIgnore
    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private Set<Lecture> lectures = new HashSet<>();

    public User(String login, String email) {
        this.login = login;
        this.email = email;
    }

    public boolean isRegisterdForAnyLecture(){
        return !ObjectUtils.isEmpty(this.lectures);
    }

    public UserDTO getUserInfo(){
        return new UserDTO(
                this.getLogin(),
                this.getEmail()
        );
    }
}
