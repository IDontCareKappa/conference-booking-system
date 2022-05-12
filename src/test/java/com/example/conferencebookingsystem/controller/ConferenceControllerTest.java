package com.example.conferencebookingsystem.controller;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ConferenceControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void itShouldGetConferenceSchedule() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/api/schedule"))
                .andExpect(ResultMatcher.matchAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.[0].topic", Is.is("Cyberbezpieczenstwo w systemach mobilnych")),
                        jsonPath("$.[6].topic", Is.is("Dockeryzacja aplikacji")),
                        jsonPath("$.[8].timeStart", Is.is("01-06-2021 14:00"))))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void itShoulGetUserSchedule() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/api/user/egenese0"))
                .andExpect(ResultMatcher.matchAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.[0].topic", Is.is("Cyberbezpieczenstwo w systemach Linux")),
                        jsonPath("$.[0].timeStart", Is.is("01-06-2021 10:00")),
                        jsonPath("$.[0].timeEnd", Is.is("01-06-2021 11:45"))))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void itShouldRegisterUserForLecture() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/api/reservation?lectureId=4&login=John&email=john@gmail.com"))
                .andExpect(ResultMatcher.matchAll(status().isOk()))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void itShouldCancelReservation() throws Exception {

        mvc.perform(MockMvcRequestBuilders.delete("/api/cancel?lectureId=4&login=sfoch3"))
                .andExpect(ResultMatcher.matchAll(status().isOk()))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void itShouldUpdateEmail() throws Exception {

        mvc.perform(MockMvcRequestBuilders.patch("/api/update?login=sfoch3&newEmail=test@gmail.com"))
                .andExpect(ResultMatcher.matchAll(status().isOk()))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void itShouldGetRegisteredUsers() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(ResultMatcher.matchAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.[3].login", Is.is("adrowsfield2")),
                        jsonPath("$.[3].email", Is.is("habramovic2@huffingtonpost.com")),
                        jsonPath("$.[1].login", Is.is("egenese0")),
                        jsonPath("$.[1].email", Is.is("abahia0@nydailynews.com"))))
                .andDo(MockMvcResultHandlers.print());

    }
}