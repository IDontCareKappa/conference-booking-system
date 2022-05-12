package com.example.conferencebookingsystem.service;

import com.example.conferencebookingsystem.model.dto.LectureStatsDTO;
import com.example.conferencebookingsystem.repository.LectureRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        LectureStatsDTO stats1 = new LectureStatsDTO(
                "Cyberbezpieczenstwo w systemach mobilnych",
                LocalDateTime.of(2021, 6, 1, 10, 0),
                9.09
        );
        LectureStatsDTO stats2 = new LectureStatsDTO(
                "Dockeryzacja aplikacji",
                LocalDateTime.of(2021, 6, 1, 14, 0),
                0.0
        );

        //when
        List<LectureStatsDTO> expected = underTest.getLecturesStats();

        //then
        assertEquals(9, expected.size());
        assertEquals(stats1.getTopic(), expected.get(0).getTopic());
        assertEquals(stats1.getTimeStart(), expected.get(0).getTimeStart());
        assertEquals(stats1.getInterest(), expected.get(0).getInterest());
        assertEquals(stats2.getTopic(), expected.get(6).getTopic());
        assertEquals(stats2.getTimeStart(), expected.get(6).getTimeStart());
        assertEquals(stats2.getInterest(), expected.get(6).getInterest());

    }

    @Test
    void itShouldReturnTopicsStatsInfo() {

        //given
        LectureStatsDTO stats1 = new LectureStatsDTO(
                "Backend development",
                LocalDateTime.of(2021, 6, 1, 12, 0),
                0.0
        );
        LectureStatsDTO stats2 = new LectureStatsDTO(
                "Szybka budowa kontenerow na bazie plikow Dockerfile",
                LocalDateTime.of(2021, 6, 1, 14, 0),
                60.0
        );

        //when
        List<LectureStatsDTO> expected = underTest.getTopicsStats();

        //then
        assertEquals(9, expected.size());
        assertEquals(stats1.getTopic(), expected.get(4).getTopic());
        assertEquals(stats1.getTimeStart(), expected.get(4).getTimeStart());
        assertEquals(stats1.getInterest(), expected.get(4).getInterest());
        assertEquals(stats2.getTopic(), expected.get(8).getTopic());
        assertEquals(stats2.getTimeStart(), expected.get(8).getTimeStart());
        assertEquals(stats2.getInterest(), expected.get(8).getInterest());

    }
}