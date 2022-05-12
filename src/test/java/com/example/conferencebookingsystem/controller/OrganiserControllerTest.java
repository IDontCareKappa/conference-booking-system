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
class OrganiserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void itShouldGetLecturesStatistics() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/api/lectures"))
                .andExpect(ResultMatcher.matchAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.[2].topic", Is.is("Cyberbezpieczenstwo w systemach Linux")),
                        jsonPath("$.[2].timeStart", Is.is("01-06-2021 10:00")),
                        jsonPath("$.[2].interest", Is.is(18.18)),
                        jsonPath("$.[7].topic", Is.is("Narzedzie Docker Compose w akcji")),
                        jsonPath("$.[7].timeStart", Is.is("01-06-2021 14:00")),
                        jsonPath("$.[7].interest", Is.is(18.18))))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void itShouldGetTopicsStatistics() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/api/topics"))
                .andExpect(ResultMatcher.matchAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.[0].topic", Is.is("Cyberbezpieczenstwo w systemach mobilnych")),
                        jsonPath("$.[0].timeStart", Is.is("01-06-2021 10:00")),
                        jsonPath("$.[0].interest", Is.is(33.33)),
                        jsonPath("$.[8].topic", Is.is("Szybka budowa kontenerow na bazie plikow Dockerfile")),
                        jsonPath("$.[8].timeStart", Is.is("01-06-2021 14:00")),
                        jsonPath("$.[8].interest", Is.is(60.0))))
                .andDo(MockMvcResultHandlers.print());

    }
}