package com.example.conferencebookingsystem.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "lectures")
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;

    private LocalDateTime timeStart;

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

    public Lecture() {
    }

    public Lecture(String topic, LocalDateTime timeStart, LocalDateTime timeEnd) {
        this.topic = topic;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }
}
