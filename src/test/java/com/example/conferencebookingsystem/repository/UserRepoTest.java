package com.example.conferencebookingsystem.repository;

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
    void itShouldGetUserByLogin() {
        //given
        String login = "slemarchant5";
        String email = "dossenna5@cnet.com";

        //when
        User expected = underTest.getByLogin(login);

        //then
        assertThat(expected.getLogin()).isEqualTo(login);
        assertThat(expected.getEmail()).isEqualTo(email);
    }
}