package com.example.conferencebookingsystem.controller;

import com.example.conferencebookingsystem.service.OrganiserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class OrganiserController {

    private final OrganiserService organiserService;

    @GetMapping("/lectures")
    public List<String> getLecturesStatistics(){
        return organiserService.getLecturesStats();
    }

    @GetMapping("/topics")
    public List<String> getTopicsStatistics(){
        return organiserService.getTopicsStats();
    }

}
