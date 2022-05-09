package com.example.conferencebookingsystem.exception;

public class LectureException extends RuntimeException {

    private final LectureError lectureError;

    public LectureException(LectureError lectureError) {
        this.lectureError = lectureError;
    }

    public LectureError getLectureError() {
        return lectureError;
    }


}
