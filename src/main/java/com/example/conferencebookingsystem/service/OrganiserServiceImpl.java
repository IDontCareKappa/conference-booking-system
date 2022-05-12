package com.example.conferencebookingsystem.service;

import com.example.conferencebookingsystem.model.dto.LectureStatsDTO;
import com.example.conferencebookingsystem.model.entity.Lecture;
import com.example.conferencebookingsystem.repository.LectureRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public List<LectureStatsDTO> getLecturesStats() {
        List<Lecture> lectures = lectureRepo.findAll();
        List<LectureStatsDTO> lecturesInfo = new ArrayList<>();
        int usersCount = 0;
        for (Lecture lecture : lectures) {
            usersCount += lecture.getUsers().size();
        }

        createLecturesStats(lectures, lecturesInfo, usersCount);

        return lecturesInfo;
    }

    private void createLecturesStats(List<Lecture> lectures,
                                     List<LectureStatsDTO> lecturesInfo,
                                     int usersCount) {
        for (Lecture lecture : lectures) {
            if (usersCount == 0){
                lecturesInfo.add(
                        new LectureStatsDTO(
                                lecture.getTopic(),
                                lecture.getTimeStart(),
                                0.00
                        )
                );
            } else {
                lecturesInfo.add(
                        new LectureStatsDTO(
                                lecture.getTopic(),
                                lecture.getTimeStart(),
                                BigDecimal.valueOf(100.0 * (double) lecture.getUsers().size() / (double) usersCount)
                                        .setScale(2, RoundingMode.HALF_UP)
                                        .doubleValue()
                        )
                );
            }
        }
    }

    @Override
    public List<LectureStatsDTO> getTopicsStats() {
        List<Lecture> lectures = lectureRepo.findAll();
        List<LectureStatsDTO> topicsInfo = new ArrayList<>();
        Dictionary<LocalDateTime, Integer> usersCount = new Hashtable<>();

        countUsersByLectureTime(lectures, usersCount);

        createTopicsStats(lectures, topicsInfo, usersCount);

        return topicsInfo;
    }

    private void createTopicsStats(List<Lecture> lectures, List<LectureStatsDTO> topicsInfo,
                                   Dictionary<LocalDateTime, Integer> usersCount) {
        for (Lecture lecture : lectures) {
            LocalDateTime timeStart = lecture.getTimeStart();
            if (usersCount.get(timeStart) == 0){
                topicsInfo.add(
                        new LectureStatsDTO(
                                lecture.getTopic(),
                                lecture.getTimeStart(),
                                0.00
                        )
                );
            } else {
                topicsInfo.add(
                        new LectureStatsDTO(
                                lecture.getTopic(),
                                lecture.getTimeStart(),
                                BigDecimal.valueOf(100.0 * (double) lecture.getUsers().size() / (double) usersCount.get(timeStart))
                                        .setScale(2, RoundingMode.HALF_UP)
                                        .doubleValue()
                        )
                );
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

}
