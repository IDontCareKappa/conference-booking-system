package com.example.conferencebookingsystem.controller;

import com.example.conferencebookingsystem.model.dto.LectureDTO;
import com.example.conferencebookingsystem.model.dto.UserDTO;
import com.example.conferencebookingsystem.service.ConferenceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class ConferenceController {

    private final ConferenceService conferenceService;

    @GetMapping("/schedule")
    public List<LectureDTO> getConferenceSchedule(){
        return conferenceService.getConferenceSchedule();
    }

    @GetMapping("/user/{login}")
    public List<LectureDTO> getUserSchedule(@PathVariable @Valid String login){
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
    public List<UserDTO> getRegisteredUsers(){
        return conferenceService.getRegisteredUsers();
    }


}
