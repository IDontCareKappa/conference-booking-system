package com.example.conferencebookingsystem;

import com.example.conferencebookingsystem.model.Lecture;
import com.example.conferencebookingsystem.model.User;
import com.example.conferencebookingsystem.repository.LectureRepo;
import com.example.conferencebookingsystem.repository.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ConferenceBookingSystemApplication {

    public ConferenceBookingSystemApplication(LectureRepo conferenceRepo, UserRepo userRepo) {
        this.lectureRepo = conferenceRepo;
        this.userRepo = userRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConferenceBookingSystemApplication.class, args);
    }

    private final LectureRepo lectureRepo;
    private final UserRepo userRepo;

    @Value("${conferenceTopics}")
    List<String> topics;

    @EventListener(ContextRefreshedEvent.class)
    public void initConferencePlan(){

        Lecture l1 = new Lecture(topics.get(0),
                LocalDateTime.of(2021, 6, 1, 10, 0),
                LocalDateTime.of(2021, 6, 1, 11, 45));
        Lecture l2 = new Lecture(topics.get(1),
                LocalDateTime.of(2021, 6, 1, 10, 0),
                LocalDateTime.of(2021, 6, 1, 11, 45));
        Lecture l3 = new Lecture(topics.get(2),
                LocalDateTime.of(2021, 6, 1, 10, 0),
                LocalDateTime.of(2021, 6, 1, 11, 45));

        Lecture l4 = new Lecture(topics.get(3),
                LocalDateTime.of(2021, 6, 1, 12, 0),
                LocalDateTime.of(2021, 6, 1, 13, 45));
        Lecture l5 = new Lecture(topics.get(4),
                LocalDateTime.of(2021, 6, 1, 12, 0),
                LocalDateTime.of(2021, 6, 1, 13, 45));
        Lecture l6 = new Lecture(topics.get(5),
                LocalDateTime.of(2021, 6, 1, 12, 0),
                LocalDateTime.of(2021, 6, 1, 13, 45));

        Lecture l7 = new Lecture(topics.get(6),
                LocalDateTime.of(2021, 6, 1, 14, 0),
                LocalDateTime.of(2021, 6, 1, 15, 45));
        Lecture l8 = new Lecture(topics.get(7),
                LocalDateTime.of(2021, 6, 1, 14, 0),
                LocalDateTime.of(2021, 6, 1, 15, 45));
        Lecture l9 = new Lecture(topics.get(8),
                LocalDateTime.of(2021, 6, 1, 14, 0),
                LocalDateTime.of(2021, 6, 1, 15, 45));

        lectureRepo.saveAll(Arrays.asList(l1,l2,l3,l4,l5,l6,l7,l8,l9));

        User user = new User("Tomasz", "tomasz@gmail.com");
        userRepo.save(user);

        l1.getUsers().addAll(Arrays.asList(user));
        lectureRepo.save(l1);

        user = new User("Agata", "agata@gmail.com");
        userRepo.save(user);
    }

}
