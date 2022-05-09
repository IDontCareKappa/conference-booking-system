package com.example.conferencebookingsystem.controller;

import com.example.conferencebookingsystem.model.Lecture;
import com.example.conferencebookingsystem.service.ConferenceService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ConferenceController {

    private final ConferenceService conferenceService;

    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @GetMapping("/conference")
    public List<Lecture> getAll(){
        return conferenceService.getAll();
    }

    @GetMapping("/schedule")
    public List<String> getTopics(){
        return conferenceService.getConferenceSchedule();
    }

    @GetMapping("/user/{login}")
    public List<String> getUserSchedule(@PathVariable @Valid String login){
        return conferenceService.getUserConferenceSchedule(login);
    }

    @PostMapping("/reservation")
    public void registerUserForLecture(@RequestParam Long lectureId,
                                       @RequestParam @Valid String login,
                                       @RequestParam @Valid String email){
        conferenceService.registerUserForLecture(lectureId, login, email);
    }

    @DeleteMapping("/cancel")
    public void cancelReservation(@RequestParam Long lectureId,
                                  @RequestParam @Valid String login){
        conferenceService.cancelReservation(lectureId, login);
    }

    @PatchMapping("/update")
    public void updateEmail(@RequestParam @Valid String login,
                            @RequestParam @Valid String newEmail){
        conferenceService.updateEmail(login, newEmail);
    }

    @GetMapping("/users")
    public List<String> getRegisteredUsers(){
        return conferenceService.getRegisteredUsers();
    }

    @GetMapping("/lectures")
    public List<String> getLecturesStatistics(){
        return conferenceService.getLecturesInfo();
    }

    @GetMapping("topics")
    public List<String> getTopicsStatistics(){
        return conferenceService.getTopicsInfo();
    }

}
