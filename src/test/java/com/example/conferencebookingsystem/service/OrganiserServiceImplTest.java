package com.example.conferencebookingsystem.service;

import com.example.conferencebookingsystem.model.dto.LectureStatsDTO;
import com.example.conferencebookingsystem.repository.LectureRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class OrganiserServiceImplTest {

    @Autowired
    private LectureRepo lectureRepo;
    private OrganiserServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new OrganiserServiceImpl(lectureRepo);
    }

    @Test
    void itShouldReturnLecturesStatsInfo() {

        //given
        List<String> stats = new ArrayList<>();
        stats.add("(06-01-2021 10:00) Cyberbezpieczenstwo w systemach mobilnych - 9,09%");
        stats.add("(06-01-2021 10:00) Cyberbezpieczenstwo serwisow internetowych - 0,00%");
        stats.add("(06-01-2021 10:00) Cyberbezpieczenstwo w systemach Linux - 18,18%");
        stats.add("(06-01-2021 12:00) Frontend development - 18,18%");
        stats.add("(06-01-2021 12:00) Backend development - 0,00%");
        stats.add("(06-01-2021 12:00) Fullstack development - 9,09%");
        stats.add("(06-01-2021 14:00) Dockeryzacja aplikacji - 0,00%");
        stats.add("(06-01-2021 14:00) Narzedzie Docker Compose w akcji - 18,18%");
        stats.add("(06-01-2021 14:00) Szybka budowa kontenerow na bazie plikow Dockerfile - 27,27%");

        //when
        List<LectureStatsDTO> expected = underTest.getLecturesStats();

        //then
        assertThat(expected).isEqualTo(stats);

    }

    @Test
    void itShouldReturnTopicsStatsInfo() {

        //given
        List<String> stats = new ArrayList<>();
        stats.add("(06-01-2021 10:00) Cyberbezpieczenstwo w systemach mobilnych - 33,33%");
        stats.add("(06-01-2021 10:00) Cyberbezpieczenstwo serwisow internetowych - 0,00%");
        stats.add("(06-01-2021 10:00) Cyberbezpieczenstwo w systemach Linux - 66,67%");
        stats.add("(06-01-2021 12:00) Frontend development - 66,67%");
        stats.add("(06-01-2021 12:00) Backend development - 0,00%");
        stats.add("(06-01-2021 12:00) Fullstack development - 33,33%");
        stats.add("(06-01-2021 14:00) Dockeryzacja aplikacji - 0,00%");
        stats.add("(06-01-2021 14:00) Narzedzie Docker Compose w akcji - 40,00%");
        stats.add("(06-01-2021 14:00) Szybka budowa kontenerow na bazie plikow Dockerfile - 60,00%");

        //when
        List<LectureStatsDTO> expected = underTest.getTopicsStats();

        //then
        assertThat(expected).isEqualTo(stats);

    }
}