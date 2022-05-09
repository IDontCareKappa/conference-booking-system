package com.example.conferencebookingsystem.service;

import com.example.conferencebookingsystem.model.Lecture;
import com.example.conferencebookingsystem.repository.LectureRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

@AllArgsConstructor
@Service
public class OrganiserServiceImpl implements OrganiserService {

    private final LectureRepo lectureRepo;

    @Override
    public List<String> getLecturesStats() {
        List<Lecture> lectures = lectureRepo.findAll();
        List<String> lecturesInfo = new ArrayList<>();
        int usersCount = 0;
        for (Lecture lecture : lectures) {
            usersCount += lecture.getUsers().size();
        }

        createLecturesStats(lectures, lecturesInfo, usersCount);

        return lecturesInfo;
    }

    private void createLecturesStats(List<Lecture> lectures, List<String> lecturesInfo, int usersCount) {
        for (Lecture lecture : lectures) {
            if (usersCount == 0){
                lecturesInfo.add("(" + formatDateTime(lecture.getTimeStart()) + ") " + lecture.getTopic() + " - 0,00%");
            } else {
                lecturesInfo.add("(" + formatDateTime(lecture.getTimeStart()) + ") " + lecture.getTopic() + " - " +
                        String.format("%.2f", (100.0 * (double) lecture.getUsers().size() / (double) usersCount)) + "%");
            }
        }
    }

    @Override
    public List<String> getTopicsStats() {
        List<Lecture> lectures = lectureRepo.findAll();
        List<String> topicsInfo = new ArrayList<>();
        Dictionary<LocalDateTime, Integer> usersCount = new Hashtable<>();

        countUsersByLectureTime(lectures, usersCount);

        createTopicsStats(lectures, topicsInfo, usersCount);

        return topicsInfo;
    }

    private void createTopicsStats(List<Lecture> lectures, List<String> topicsInfo,
                                   Dictionary<LocalDateTime, Integer> usersCount) {
        for (Lecture lecture : lectures) {
            LocalDateTime timeStart = lecture.getTimeStart();
            if (usersCount.get(timeStart) == 0){
                topicsInfo.add("(" + formatDateTime(timeStart) + ") " + lecture.getTopic() + " - 0,00%");
            } else {
                topicsInfo.add("(" + formatDateTime(timeStart) + ") " + lecture.getTopic() + " - " +
                        String.format("%.2f", (100.0 * (double) lecture.getUsers().size() / (double) usersCount.get(timeStart))) + "%");
            }
        }
    }

    private void countUsersByLectureTime(List<Lecture> lectures, Dictionary<LocalDateTime, Integer> usersCount) {
        for (Lecture lecture : lectures) {
            LocalDateTime key = lecture.getTimeStart();
            if (usersCount.get(key) == null){
                usersCount.put(key, lecture.getUsers().size());
            } else {
                usersCount.put(key, usersCount.get(key) + lecture.getUsers().size());
            }
        }
    }

    public String formatDateTime(LocalDateTime date){
        return date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm"));
    }

}
