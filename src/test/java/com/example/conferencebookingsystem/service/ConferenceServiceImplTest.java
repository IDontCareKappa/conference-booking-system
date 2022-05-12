package com.example.conferencebookingsystem.service;

import com.example.conferencebookingsystem.model.dto.LectureDTO;
import com.example.conferencebookingsystem.model.dto.UserDTO;
import com.example.conferencebookingsystem.model.entity.User;
import com.example.conferencebookingsystem.repository.LectureRepo;
import com.example.conferencebookingsystem.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        LectureDTO firstElement = new LectureDTO(
                "Cyberbezpieczenstwo w systemach mobilnych",
                LocalDateTime.of(2021, 6,1,10,0),
                LocalDateTime.of(2021,6,1,11,45)
        );

        LectureDTO lastElement = new LectureDTO(
                "Szybka budowa kontenerow na bazie plikow Dockerfile",
                LocalDateTime.of(2021, 6,1,14,0),
                LocalDateTime.of(2021,6,1,15,45)
        );

        //when
        List<LectureDTO> expected = underTest.getConferenceSchedule();

        //then
        assertEquals(9, expected.size());
        assertEquals(firstElement.getTopic(), expected.get(0).getTopic());
        assertEquals(firstElement.getTimeStart(), expected.get(0).getTimeStart());
        assertEquals(firstElement.getTimeEnd(), expected.get(0).getTimeEnd());
        assertEquals(lastElement.getTopic(), expected.get(8).getTopic());
        assertEquals(lastElement.getTimeStart(), expected.get(8).getTimeStart());
        assertEquals(lastElement.getTimeEnd(), expected.get(8).getTimeEnd());

    }

    @Test
    void itShouldReturnUserConferenceSchedule() {

        //given
        String userLogin = "mwharmby8";

        List<LectureDTO> userSchedule = List.of(
                new LectureDTO(
                        "Frontend development",
                        LocalDateTime.of(2021, 6,1,12,0),
                        LocalDateTime.of(2021, 6,1,13,45)
                ),
                new LectureDTO(
                        "Narzedzie Docker Compose w akcji",
                        LocalDateTime.of(2021, 6,1,14,0),
                        LocalDateTime.of(2021, 6,1,15,45)
                )
        );

        //when
        List<LectureDTO> expected = underTest.getUserConferenceSchedule(userLogin);

        //then
        assertEquals(2, expected.size());
        assertThat(expected.get(0).getTopic())
                .isIn(userSchedule.get(0).getTopic(), userSchedule.get(1).getTopic());

        assertThat(expected.get(0).getTimeStart())
                .isIn(userSchedule.get(0).getTimeStart(), userSchedule.get(1).getTimeStart());

        assertThat(expected.get(0).getTimeEnd())
                .isIn(userSchedule.get(0).getTimeEnd(), userSchedule.get(1).getTimeEnd());


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
        User expected = userRepo.getByLogin(login);

        //then
        assertEquals(expected.getLogin(), user.getLogin());
        assertEquals(expected.getEmail(), user.getEmail());

    }

    @Test
    void itShouldCancelUserReservation() {

        //given
        Long lectureId = 4L;
        String login = "mwharmby8";
        LectureDTO lectureDTO = new LectureDTO(
                "Narzedzie Docker Compose w akcji",
                LocalDateTime.of(2021, 6,1,14,0),
                LocalDateTime.of(2021, 6,1,15,45)
        );

        //when
        underTest.cancelReservation(lectureId, login);
        List<LectureDTO> expected = underTest.getUserConferenceSchedule(login);

        //then
        assertEquals(1, expected.size());
        assertEquals(lectureDTO.getTopic(), expected.get(0).getTopic());
        assertEquals(lectureDTO.getTimeStart(), expected.get(0).getTimeStart());
        assertEquals(lectureDTO.getTimeEnd(), expected.get(0).getTimeEnd());

    }

    @Test
    void itShouldUpdateUserEmail() {

        //given
        String login = "mwharmby8";
        String newEmail = "bcockney8@symantec.com";
        User user = userRepo.getByLogin(login);

        //when
        underTest.updateEmail(login, newEmail);
        User expected = userRepo.getByLogin(login);

        //then
        assertEquals(expected.getLogin(), user.getLogin());
        assertEquals(expected.getEmail(), user.getEmail());

    }

    @Test
    void itShouldReturnRegisteredUsers() {

        //given
        UserDTO user1 = new UserDTO(
                "nkeely1",
                "ceykel1@merriam-webster.com"
        );

        UserDTO user2 = new UserDTO(
                "aantuk6",
                "dmcallan6@xinhuanet.com"
        );

        //when
        List<UserDTO> expected = underTest.getRegisteredUsers();

        //then
        assertEquals(10, expected.size());
        assertEquals(user1.getLogin(), expected.get(2).getLogin());
        assertEquals(user1.getEmail(), expected.get(2).getEmail());
        assertEquals(user2.getLogin(), expected.get(7).getLogin());
        assertEquals(user2.getEmail(), expected.get(7).getEmail());

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
        assertEquals(expected, formatedDate);

    }
}