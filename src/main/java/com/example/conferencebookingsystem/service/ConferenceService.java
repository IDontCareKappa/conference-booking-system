package com.example.conferencebookingsystem.service;

import com.example.conferencebookingsystem.model.dto.LectureDTO;
import com.example.conferencebookingsystem.model.dto.UserDTO;

import java.util.List;


public interface ConferenceService {

    List<LectureDTO> getConferenceSchedule();

    List<LectureDTO> getUserConferenceSchedule(String login);

    void registerUserForLecture(Long lectureId, String login, String email);

    void cancelReservation(Long lectureId, String login);

    void updateEmail(String login, String newEmail);

    List<UserDTO> getRegisteredUsers();

}
