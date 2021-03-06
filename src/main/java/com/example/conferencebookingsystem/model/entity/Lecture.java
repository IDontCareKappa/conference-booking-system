package com.example.conferencebookingsystem.model.entity;

import com.example.conferencebookingsystem.model.dto.LectureDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "lecture")
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String topic;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    @NotNull
    private LocalDateTime timeStart;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    @NotNull
    private LocalDateTime timeEnd;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "users_lectures",
            joinColumns = {
                    @JoinColumn(name = "lecture_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private Set<User> users = new HashSet<>();

    public Lecture(String topic, LocalDateTime timeStart, LocalDateTime timeEnd) {
        this.topic = topic;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public LectureDTO getLectureInfo(){
        return new LectureDTO(
                this.topic,
                this.timeStart,
                this.timeEnd
        );
    }
}
