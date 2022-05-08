package com.example.conferencebookingsystem;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.w3c.dom.ls.LSOutput;

import java.util.Date;

@SpringBootApplication
public class ConferenceBookingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConferenceBookingSystemApplication.class, args);
    }

}
