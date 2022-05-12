package com.example.conferencebookingsystem.repository;

import com.example.conferencebookingsystem.exception.UserError;
import com.example.conferencebookingsystem.exception.UserException;
import com.example.conferencebookingsystem.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepoTest {

    @Autowired
    private UserRepo underTest;

    @Test
    void itShouldFindUserByLogin() {
        //given
        String login = "slemarchant5";
        User user = new User(
                login,
                "dossenna5@cnet.com"
        );
        underTest.save(user);

        //when
        User expected = underTest.findFirstByLogin(login)
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));

        //then
        assertThat(expected.getLogin()).isEqualTo(user.getLogin());
        assertThat(expected.getEmail()).isEqualTo(user.getEmail());
    }
}