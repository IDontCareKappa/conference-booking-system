package com.example.conferencebookingsystem.repository;

import com.example.conferencebookingsystem.exception.UserError;
import com.example.conferencebookingsystem.exception.UserException;
import com.example.conferencebookingsystem.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String userLogin);
    Optional<User> findByEmail(String userEmail);

    default User getByLogin(String userLogin){
        if (userLogin.isEmpty() || userLogin.isBlank()){
            throw new UserException(UserError.USER_LOGIN_EMPTY);
        }
        return findByLogin(userLogin)
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));
    }

    default User getByEmail(String userEmail){
        if (userEmail.isEmpty() || userEmail.isBlank()){
            throw new UserException(UserError.USER_EMAIL_EMPTY);
        }
        return findByEmail(userEmail)
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));
    }

}
