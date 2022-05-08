package com.example.conferencebookingsystem.service;

import com.example.conferencebookingsystem.model.Lecture;

import java.util.List;


public interface ConferenceService {

    public List<Lecture> getAll();

    public List<String> getConferenceSchedule();

    public List<String> getUserConferenceSchedule(String login);

    public void registerUserForLecture(Long lectureId, String login, String email);

    public void cancelReservation(Long lectureId, String login);

    public void updateEmail(String login, String newEmail);

}
