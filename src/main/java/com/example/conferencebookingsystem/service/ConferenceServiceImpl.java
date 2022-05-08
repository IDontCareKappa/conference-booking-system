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
    public void registerUserForLecture(Long lectureId, String login, String email) {
        Lecture lecture = lectureRepo.findById(lectureId)
                .orElseThrow(() -> new LectureException(LectureError.LECTURE_NOT_FOUND));

        if (lecture.getUsers().size() == 5){
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
    }

    private void checkUserTimeAvailability(Lecture lecture, Optional<User> user) {
        Set<Lecture> userLectures = user.get().getLectures();
        for (Lecture l : userLectures) {
            if (l.getTimeStart().equals(lecture.getTimeStart())){
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
}
