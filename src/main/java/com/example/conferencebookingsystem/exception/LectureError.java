package com.example.conferencebookingsystem.exception;

public enum LectureError {

    LECTURE_IS_FULL("Brak miejsc na wybraną prelekcje"),
    LECTURE_NOT_FOUND("Prelekcja nie istnieje"),
    LECTURE_USER_NOT_FOUND("Użytkownik nie jest zapisany na daną prelekcje");

    private final String message;

    LectureError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
