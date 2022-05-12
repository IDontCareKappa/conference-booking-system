package com.example.conferencebookingsystem.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LectureDTO {

    private String topic;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime timeStart;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime timeEnd;

}
