package com.example.conferencebookingsystem.repository;

import com.example.conferencebookingsystem.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepo extends JpaRepository<Lecture, Long> {
}
