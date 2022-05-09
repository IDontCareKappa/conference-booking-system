package com.example.conferencebookingsystem.repository;

import com.example.conferencebookingsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    public Optional<User> findFirstByLogin(String login);

}
