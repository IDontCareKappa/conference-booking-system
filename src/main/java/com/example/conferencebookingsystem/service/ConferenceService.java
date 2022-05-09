package com.example.conferencebookingsystem.service;

import java.util.List;


public interface ConferenceService {

    List<String> getConferenceSchedule();

    List<String> getUserConferenceSchedule(String login);

    void registerUserForLecture(Long lectureId, String login, String email);

    void cancelReservation(Long lectureId, String login);

    void updateEmail(String login, String newEmail);

    List<String> getRegisteredUsers();

}
