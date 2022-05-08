package com.example.conferencebookingsystem.exception;

public enum LectureError {

    LECTURE_IS_FULL("Brak miejsc na wybraną prelekcję"),
    LECTURE_NOT_FOUND("Prelekcja nie istnieje");

    private String message;

    LectureError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
