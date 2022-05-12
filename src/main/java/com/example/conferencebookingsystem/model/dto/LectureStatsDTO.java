package com.example.conferencebookingsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LectureStatsDTO {

    private String topic;

    private LocalDateTime timeStart;

    private double interest;

}
