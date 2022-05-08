package com.example.conferencebookingsystem.service;

import com.example.conferencebookingsystem.exception.LectureError;
import com.example.conferencebookingsystem.exception.LectureException;
import com.example.conferencebookingsystem.exception.UserError;
import com.example.conferencebookingsystem.exception.UserException;
import com.example.conferencebookingsystem.model.Lecture;
import com.example.conferencebookingsystem.model.User;
import com.example.conferencebookingsystem.repository.LectureRepo;
import com.example.conferencebookingsystem.repository.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ConferenceServiceImpl implements ConferenceService {

    private final LectureRepo lectureRepo;
    private final UserRepo userRepo;

    public ConferenceServiceImpl(LectureRepo conferenceRepo, UserRepo userRepo) {
        this.lectureRepo = conferenceRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<Lecture> getAll() {
        return lectureRepo.findAll();
    }

    @Override
    public List<String> getConferenceSchedule() {
        List<String> schedule = new ArrayList<>();
        List<Lecture> lectures = lectureRepo.findAll();
        for (Lecture lecture :
                lectures) {
            schedule.add(lecture.getTopic() + " (" + lecture.getTimeStart() + " - " + lecture.getTimeEnd() + ")");
        }
        return schedule;
    }

    @Override
    public List<String> getUserConferenceSchedule(String login) {
        User user = userRepo.findByLogin(login)
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));

        Set<Lecture> lectures = user.getLectures();
        List<String> userLectures = new ArrayList<>();
        for (Lecture lecture :
                lectures) {
            userLectures.add(lecture.getTopic() + " " + lecture.getTimeStart() + " - " + lecture.getTimeEnd());
        }

        if (userLectures.isEmpty()) {
            throw new UserException(UserError.USER_IS_NOT_ASSIGN_TO_ANY_LECTURE);
        }

        return userLectures;
    }

    @Override
    public void registerUserForLecture(Long lectureId, String login, String email) {
        Lecture lecture = lectureRepo.findById(lectureId)
                .orElseThrow(() -> new LectureException(LectureError.LECTURE_NOT_FOUND));

        if (lecture.getUsers().size() == 5) {
            throw new LectureException(LectureError.LECTURE_IS_FULL);
        }

        Optional<User> user = userRepo.findByLogin(login);
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

            lecture.getUsers().addAll(List.of(newUser));
            lectureRepo.save(lecture);
        }

        try {
            sendEmailNotification(email, lecture.getTopic());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendEmailNotification(String userEmail, String lectureTopic) throws IOException {
        final String emailContent = LocalDateTime.now().toString() + "\n" + userEmail
                + "\n" + "Szanowny użytkowniku! Informujemy że zostałeś zapisany na prelekcję \"" + lectureTopic + "\"\n";

        try {
            File file = new File("powiadomienia.txt");
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        final Path path = Paths.get("powiadomienia.txt");

        try (
                final BufferedWriter writer = Files.newBufferedWriter(path,
                        StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        ) {
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
        User user = userRepo.findByLogin(login)
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));

        if (lecture.getUsers().contains(user)) {
            lecture.getUsers().remove(user);
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

        User user = userRepo.findByLogin(login)
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));

        user.setEmail(newEmail);
        userRepo.save(user);
    }

    @Override
    public List<String> getRegisteredUsers() {
        List<User> users = userRepo.findAll();
        List<String> registeredUsers = new ArrayList<>();

        for (User user : users) {
            if (!ObjectUtils.isEmpty(user.getLectures())) {
                registeredUsers.add(user.getLogin() + " " + user.getEmail());
            }
        }

        return registeredUsers;
    }
}
