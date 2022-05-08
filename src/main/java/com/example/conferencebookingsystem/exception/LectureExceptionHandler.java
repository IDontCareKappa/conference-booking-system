package com.example.conferencebookingsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LectureExceptionHandler {

    @ExceptionHandler(value = LectureException.class)
    public ResponseEntity<ErrorInfo> handleException(LectureException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo(e.getLectureError().getMessage()));
    }

}
