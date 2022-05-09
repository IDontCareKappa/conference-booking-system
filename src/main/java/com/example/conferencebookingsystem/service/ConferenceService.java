package com.example.conferencebookingsystem.service;

import com.example.conferencebookingsystem.model.Lecture;

import java.util.List;


public interface ConferenceService {

    List<Lecture> getAll();

    List<String> getConferenceSchedule();

    List<String> getUserConferenceSchedule(String login);

    void registerUserForLecture(Long lectureId, String login, String email);

    void cancelReservation(Long lectureId, String login);

    void updateEmail(String login, String newEmail);

    List<String> getRegisteredUsers();

    List<String> getLecturesInfo();

    List<String> getTopicsInfo();
}
