package com.example.conferencebookingsystem.service;

import com.example.conferencebookingsystem.exception.UserError;
import com.example.conferencebookingsystem.exception.UserException;
import com.example.conferencebookingsystem.model.Lecture;
import com.example.conferencebookingsystem.model.User;
import com.example.conferencebookingsystem.repository.LectureRepo;
import com.example.conferencebookingsystem.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.*;

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
            schedule.add(lecture.getTopic() + " " + lecture.getTimeStart() + " - " + lecture.getTimeEnd());
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

        if (userLectures.isEmpty()){
            throw new UserException(UserError.USER_IS_NOT_ASSIGN_TO_ANY_LECTURE);
        }

        return userLectures;
    }

    @Override
    public User registerUserForLecture(Long lectureId, String login, String email) {
        Lecture lecture = lectureRepo.findById(lectureId)
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));

        Optional<User> user = userRepo.findByLogin(login);
        if (user.isPresent()) {
            if (!user.get().getEmail().equals(email)) {
                throw new UserException(UserError.USER_LOGIN_NOT_AVAILABLE);
            }

            checkUsersOfLecture(email, lecture);

            lecture.getUsers().addAll(Arrays.asList(user.get()));
            lectureRepo.save(lecture);

            return user.get();

        } else {
            User newUser = new User(login, email);
            userRepo.save(newUser);

            lecture.getUsers().addAll(Arrays.asList(newUser));
            lectureRepo.save(lecture);

            return newUser;
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
}
