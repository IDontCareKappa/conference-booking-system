package com.example.conferencebookingsystem.service;

import com.example.conferencebookingsystem.model.Lecture;
import com.example.conferencebookingsystem.model.User;

import java.util.List;


public interface ConferenceService {

    public List<Lecture> getAll();

    public List<String> getConferenceSchedule();

    public List<String> getUserConferenceSchedule(String login);

    public User registerUserForLecture(Long lectureId, String login, String email);

}
