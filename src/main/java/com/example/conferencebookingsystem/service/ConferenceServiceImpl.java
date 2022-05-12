package com.example.conferencebookingsystem.service;

import com.example.conferencebookingsystem.exception.LectureError;
import com.example.conferencebookingsystem.exception.LectureException;
import com.example.conferencebookingsystem.exception.UserError;
import com.example.conferencebookingsystem.exception.UserException;
import com.example.conferencebookingsystem.model.Lecture;
import com.example.conferencebookingsystem.model.User;
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
import java.util.*;

@AllArgsConstructor
@Service
public class ConferenceServiceImpl implements ConferenceService {

    private final LectureRepo lectureRepo;
    private final UserRepo userRepo;

    @Override
    public List<String> getConferenceSchedule() {
        List<String> schedule = new ArrayList<>();
        List<Lecture> lectures = lectureRepo.findAll();
        createSchedule(schedule, lectures);
        return schedule;
    }

    private void createSchedule(List<String> schedule, List<Lecture> lectures) {
        for (Lecture lecture : lectures) {
            schedule.add(lecture.getTopic() + " (" +
                    formatDateTime(lecture.getTimeStart()) + " - " +
                    formatDateTime(lecture.getTimeEnd()) + ")");
        }
    }

    @Override
    public List<String> getUserConferenceSchedule(String login) {
        User user = userRepo.findFirstByLogin(login)
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));

        Set<Lecture> lectures = user.getLectures();
        List<String> userLectures = new ArrayList<>();

        createUserSchedule(lectures, userLectures);

        if (userLectures.isEmpty()) {
            throw new UserException(UserError.USER_IS_NOT_ASSIGN_TO_ANY_LECTURE);
        }

        return userLectures;
    }

    private void createUserSchedule(Set<Lecture> lectures, List<String> userLectures) {
        for (Lecture lecture : lectures) {
            userLectures.add(lecture.getTopic() + " " +
                    formatDateTime(lecture.getTimeStart()) + " - " +
                    formatDateTime(lecture.getTimeEnd()));
        }
    }

    @Override
    public void registerUserForLecture(Long lectureId, String login, String email) {
        if (ObjectUtils.isEmpty(login)){
            throw new UserException(UserError.USER_LOGIN_EMPTY);
        } else if (ObjectUtils.isEmpty(email)){
            throw new UserException(UserError.USER_EMAIL_EMPTY);
        }

        Lecture lecture = lectureRepo.findById(lectureId)
                .orElseThrow(() -> new LectureException(LectureError.LECTURE_NOT_FOUND));
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
        Optional<User> user = userRepo.findFirstByLogin(login);
        if (user.isPresent()) {
            if (!user.get().getEmail().equals(email)) {
                throw new UserException(UserError.USER_LOGIN_NOT_AVAILABLE);
            }

            checkUserTimeAvailability(lecture, user);
            checkUsersOfLecture(email, lecture);

            lecture.getUsers().addAll(List.of(user.get()));
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
        Lecture lecture = lectureRepo.findById(lectureId)
                .orElseThrow(() -> new LectureException(LectureError.LECTURE_NOT_FOUND));
        User user = userRepo.findFirstByLogin(login)
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));

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

        User user = userRepo.findFirstByLogin(login)
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));

        user.setEmail(newEmail);
        userRepo.save(user);
    }

    @Override
    public List<String> getRegisteredUsers() {
        List<User> users = userRepo.findAll();
        List<String> registeredUsers = new ArrayList<>();

        createRegisteredUsersList(users, registeredUsers);

        if (registeredUsers.isEmpty()){
            return List.of("Brak użytkowników");
        }

        return registeredUsers;
    }

    private void createRegisteredUsersList(List<User> users, List<String> registeredUsers) {
        for (User user : users) {
            if (!ObjectUtils.isEmpty(user.getLectures())) {
                registeredUsers.add(user.getLogin() + " " + user.getEmail());
            }
        }
    }

    public String formatDateTime(LocalDateTime date){
        return date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm"));
    }
}
