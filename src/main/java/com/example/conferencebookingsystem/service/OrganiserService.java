package com.example.conferencebookingsystem.service;

import com.example.conferencebookingsystem.model.dto.LectureStatsDTO;

import java.util.List;

public interface OrganiserService {

    List<LectureStatsDTO> getLecturesStats();

    List<LectureStatsDTO> getTopicsStats();

}
