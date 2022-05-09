package com.example.conferencebookingsystem.service;

import com.example.conferencebookingsystem.exception.UserError;
import com.example.conferencebookingsystem.exception.UserException;
import com.example.conferencebookingsystem.model.User;
import com.example.conferencebookingsystem.repository.LectureRepo;
import com.example.conferencebookingsystem.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ConferenceServiceImplTest {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private LectureRepo lectureRepo;
    private ConferenceServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new ConferenceServiceImpl(lectureRepo, userRepo);
    }

    @Test
    void itShouldReturnConferenceSchedule() {

        //given
        List<String> schedule = new ArrayList<>();
        schedule.add("Cyberbezpieczenstwo w systemach mobilnych (06-01-2021 10:00 - 06-01-2021 11:45)");
        schedule.add("Cyberbezpieczenstwo serwisow internetowych (06-01-2021 10:00 - 06-01-2021 11:45)");
        schedule.add("Cyberbezpieczenstwo w systemach Linux (06-01-2021 10:00 - 06-01-2021 11:45)");
        schedule.add("Frontend development (06-01-2021 12:00 - 06-01-2021 13:45)");
        schedule.add("Backend development (06-01-2021 12:00 - 06-01-2021 13:45)");
        schedule.add("Fullstack development (06-01-2021 12:00 - 06-01-2021 13:45)");
        schedule.add("Dockeryzacja aplikacji (06-01-2021 14:00 - 06-01-2021 15:45)");
        schedule.add("Narzedzie Docker Compose w akcji (06-01-2021 14:00 - 06-01-2021 15:45)");
        schedule.add("Szybka budowa kontenerow na bazie plikow Dockerfile (06-01-2021 14:00 - 06-01-2021 15:45)");

        //when
        List<String> expected = underTest.getConferenceSchedule();

        //then
        assertThat(expected).isEqualTo(schedule);

    }

    @Test
    void itShouldReturnUserConferenceSchedule() {

        //given
        String userLogin = "mwharmby8";

        List<String> userSchedule1 = new ArrayList<>();
        userSchedule1.add("Narzedzie Docker Compose w akcji 06-01-2021 14:00 - 06-01-2021 15:45");
        userSchedule1.add("Frontend development 06-01-2021 12:00 - 06-01-2021 13:45");

        List<String> userSchedule2 = new ArrayList<>();
        userSchedule2.add("Frontend development 06-01-2021 12:00 - 06-01-2021 13:45");
        userSchedule2.add("Narzedzie Docker Compose w akcji 06-01-2021 14:00 - 06-01-2021 15:45");

        //when
        List<String> expected = underTest.getUserConferenceSchedule(userLogin);

        //then
        assertThat(expected).isIn(userSchedule1, userSchedule2);

    }

    @Test
    void itShouldRegisterUserForLecture() {

        //given
        Long lectureId = 3L;
        String login = "piotr23";
        String email = "piotr23@gmail.com";
        User user = new User(
                login,
                email
        );

        //when
        underTest.registerUserForLecture(lectureId, login, email);
        User expected = userRepo.findFirstByLogin(login)
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));

        //then
        assertThat(expected.getLogin()).isEqualTo(user.getLogin());
        assertThat(expected.getEmail()).isEqualTo(user.getEmail());

    }

    @Test
    void itShouldCancelUserReservation() {

        //given
        Long lectureId = 4L;
        String login = "mwharmby8";
        List<String> userSchedule = new ArrayList<>();
        userSchedule.add("Narzedzie Docker Compose w akcji 06-01-2021 14:00 - 06-01-2021 15:45");

        //when
        underTest.cancelReservation(lectureId, login);
        List<String> expected = underTest.getUserConferenceSchedule(login);

        //then
        assertThat(expected).isEqualTo(userSchedule);

    }

    @Test
    void itShouldUpdateUserEmail() {

        //given
        String login = "mwharmby8";
        String newEmail = "bcockney8@symantec.com";
        User user = userRepo.findFirstByLogin(login)
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));

        //when
        underTest.updateEmail(login, newEmail);
        User expected = userRepo.findFirstByLogin(login)
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));

        //then
        assertThat(expected.getLogin()).isEqualTo(user.getLogin());
        assertThat(expected.getEmail()).isEqualTo(user.getEmail());

    }

    @Test
    void itShouldReturnRegisteredUsers() {

        //given
        List<String> registeredUsers = new ArrayList<>();
        registeredUsers.add("etoothill9 cbernli9@mlb.com");
        registeredUsers.add("egenese0 abahia0@nydailynews.com");
        registeredUsers.add("nkeely1 ceykel1@merriam-webster.com");
        registeredUsers.add("adrowsfield2 habramovic2@huffingtonpost.com");
        registeredUsers.add("sfoch3 tglaze3@twitpic.com");
        registeredUsers.add("emacgibbon4 bheinlein4@smh.com.au");
        registeredUsers.add("slemarchant5 dossenna5@cnet.com");
        registeredUsers.add("aantuk6 dmcallan6@xinhuanet.com");
        registeredUsers.add("jsigars7 bhugk7@devhub.com");
        registeredUsers.add("mwharmby8 bcockney8@symantec.com");

        //when
        List<String> expected = underTest.getRegisteredUsers();

        //then
        assertThat(expected).isEqualTo(registeredUsers);

    }

    @Test
    void itShouldReturnFormatedDateTime() {

        //given
        String dateStr = "2021-01-24T15:30";
        LocalDateTime date = LocalDateTime.parse(dateStr);
        String formatedDate = "01-24-2021 15:30";

        //when
        String expected = underTest.formatDateTime(date);

        //then
        assertThat(expected).isEqualTo(formatedDate);

    }
}