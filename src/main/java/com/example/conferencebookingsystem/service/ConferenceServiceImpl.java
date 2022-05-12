package com.example.conferencebookingsystem.service;

import com.example.conferencebookingsystem.exception.LectureError;
import com.example.conferencebookingsystem.exception.LectureException;
import com.example.conferencebookingsystem.exception.UserError;
import com.example.conferencebookingsystem.exception.UserException;
import com.example.conferencebookingsystem.model.dto.LectureDTO;
import com.example.conferencebookingsystem.model.dto.UserDTO;
import com.example.conferencebookingsystem.model.entity.Lecture;
import com.example.conferencebookingsystem.model.entity.User;
import com.example.conferencebookingsystem.repository.LectureRepo;
import com.example.conferencebookingsystem.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class ConferenceServiceImpl implements ConferenceService {

    private final LectureRepo lectureRepo;
    private final UserRepo userRepo;

    @Override
    public List<LectureDTO> getConferenceSchedule() {
        List<LectureDTO> schedule = new ArrayList<>();
        List<Lecture> lectures = lectureRepo.getAll();

        lectures.forEach(lecture -> schedule.add(lecture.getLectureInfo()));

        return schedule;
    }

    @Override
    public List<LectureDTO> getUserConferenceSchedule(String login) {
        User user = userRepo.getByLogin(login);

        Set<Lecture> lectures = user.getLectures();
        List<LectureDTO> userLectures = new ArrayList<>();

        lectures.forEach(lecture -> userLectures.add(lecture.getLectureInfo()));

        if (userLectures.isEmpty()) {
            throw new UserException(UserError.USER_IS_NOT_ASSIGN_TO_ANY_LECTURE);
        }

        return userLectures;
    }

    @Override
    public void registerUserForLecture(Long lectureId, String login, String email) {
        if (ObjectUtils.isEmpty(login)){
            throw new UserException(UserError.USER_LOGIN_EMPTY);
        } else if (ObjectUtils.isEmpty(email)){
            throw new UserException(UserError.USER_EMAIL_EMPTY);
        }

        Lecture lecture = lectureRepo.getByID(lectureId);
        if (lecture.getUsers().size() >= 5) {
            throw new LectureException(LectureError.LECTURE_IS_FULL);
        }

        makeReservation(login, email, lecture);

        try {
            sendEmailNotification(email, lecture.getTopic());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makeReservation(String login, String email, Lecture lecture) {
        Optional<User> userByLogin = userRepo.findByLogin(login);
        Optional<User> userByEmail = userRepo.findByEmail(email);

        if (userByEmail.isPresent()){
            if (!userByEmail.get().getLogin().equals(login)) {
                throw new UserException(UserError.USER_EMAIL_NOT_AVAILABLE);
            }
        }

        if (userByLogin.isPresent()) {
            if (!userByLogin.get().getEmail().equals(email)) {
                throw new UserException(UserError.USER_LOGIN_NOT_AVAILABLE);
            }



            checkUserTimeAvailability(lecture, userByLogin);
            checkUsersOfLecture(email, lecture);

            lecture.getUsers().addAll(List.of(userByLogin.get()));
            lectureRepo.save(lecture);
        } else {
            User newUser = new User(login, email);
            userRepo.save(newUser);

            lecture.getUsers().add(newUser);
            lectureRepo.save(lecture);
        }
    }

    private void sendEmailNotification(String userEmail, String lectureTopic) throws IOException {
        final String emailContent = formatDateTime(LocalDateTime.now()) + "\n" + userEmail
                + "\n" + "Szanowny użytkowniku! Informujemy ze zostales zapisany na prelekcje \""
                + lectureTopic + "\".\n";

        try {
            File file = new File("powiadomienia.txt");
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        final Path path = Paths.get("powiadomienia.txt");

        try (final BufferedWriter writer = Files.newBufferedWriter(path,
                        StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            writer.write(emailContent);
            writer.flush();
        }

    }

    private void checkUserTimeAvailability(Lecture lecture, Optional<User> user) {
        Set<Lecture> userLectures = user.get().getLectures();
        for (Lecture l : userLectures) {
            if (l.getTimeStart().equals(lecture.getTimeStart())) {
                throw new UserException(UserError.USER_ASSIGNED_FOR_THIS_TIME);
            }
        }
    }

    private void checkUsersOfLecture(String email, Lecture lecture) {
        Set<User> users = lecture.getUsers();
        for (User u : users) {
            if (u.getEmail().equals(email)) {
                throw new UserException(UserError.USER_ALREADY_ASSIGNED);
            }
        }
    }

    @Override
    public void cancelReservation(Long lectureId, String login) {
        Lecture lecture = lectureRepo.getByID(lectureId);
        User user = userRepo.getByLogin(login);

        if (lecture.getUsers().contains(user)) {
            lecture.getUsers().remove(user);
            user.getLectures().remove(lecture);
        } else {
            throw new LectureException(LectureError.LECTURE_USER_NOT_FOUND);
        }

        lectureRepo.save(lecture);
    }

    @Override
    public void updateEmail(String login, String newEmail) {
        if (ObjectUtils.isEmpty(newEmail)) {
            throw new UserException(UserError.USER_EMAIL_EMPTY);
        }

        User user = userRepo.getByLogin(login);

        user.setEmail(newEmail);
        userRepo.save(user);
    }

    @Override
    public List<UserDTO> getRegisteredUsers() {
        List<User> users = userRepo.findAll();
        List<UserDTO> registeredUsers = new ArrayList<>();

        users.forEach(user -> {
            if (user.isRegisterdForAnyLecture())
                registeredUsers.add(user.getUserInfo());
        });

        if (registeredUsers.isEmpty()){
            throw new UserException(UserError.NO_USER_IS_REGISTERED);
        }

        return registeredUsers;
    }

    public String formatDateTime(LocalDateTime date){
        return date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm"));
    }
}
