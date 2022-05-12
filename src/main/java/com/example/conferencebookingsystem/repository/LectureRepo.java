package com.example.conferencebookingsystem.repository;

import com.example.conferencebookingsystem.exception.LectureError;
import com.example.conferencebookingsystem.exception.LectureException;
import com.example.conferencebookingsystem.model.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepo extends JpaRepository<Lecture, Long> {

    default List<Lecture> getAll(){
        List<Lecture> lectures = findAll();
        if (lectures.isEmpty()){
            throw new LectureException(LectureError.NO_LECTURES_FOUND);
        }
        return lectures;
    }

    default Lecture getByID(Long lectureId){
        return findById(lectureId)
                .orElseThrow(() -> new LectureException(LectureError.LECTURE_NOT_FOUND));
    }

}
