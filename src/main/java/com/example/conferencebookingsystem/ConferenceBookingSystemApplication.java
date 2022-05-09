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

        User u1 = new User("etoothill9", "cbernli9@mlb.com");
        User u2 = new User("egenese0", "abahia0@nydailynews.com");
        User u3 = new User("nkeely1", "ceykel1@merriam-webster.com");
        User u4 = new User("adrowsfield2", "habramovic2@huffingtonpost.com");
        User u5 = new User("sfoch3", "tglaze3@twitpic.com");
        User u6 = new User("emacgibbon4", "bheinlein4@smh.com.au");
        User u7 = new User("slemarchant5", "dossenna5@cnet.com");
        User u8 = new User("aantuk6", "dmcallan6@xinhuanet.com");
        User u9 = new User("jsigars7", "bhugk7@devhub.com");
        User u10 = new User("mwharmby8", "bcockney8@symantec.com");
        userRepo.saveAll(Arrays.asList(u1,u2,u3,u4,u5,u6,u7,u8,u9,u10));

        l1.getUsers().add(u7);
        l3.getUsers().addAll(Arrays.asList(u2,u6));
        l4.getUsers().add(u5);
        l6.getUsers().add(u3);
        l8.getUsers().add(u8);
        l9.getUsers().addAll(Arrays.asList(u4,u9,u1));
        lectureRepo.saveAll(Arrays.asList(l1,l2,l3,l4,l5,l6,l7,l8,l9));

        u1 = new User("Agata", "agata@gmail.com");
        userRepo.save(u1);
    }

}
