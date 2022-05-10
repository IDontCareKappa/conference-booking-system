package com.example.conferencebookingsystem.exception;

public enum UserError {

    USER_NOT_FOUND("Uzytkownik nie istnieje"),
    USER_IS_NOT_ASSIGN_TO_ANY_LECTURE("Uzytkownik nie jest zapisany do zadnej prelekcji"),
    USER_ALREADY_ASSIGNED("Uzytkownik jest juz zapisany do tej prelekcji"),
    USER_LOGIN_NOT_AVAILABLE("Podany login jest już zajęty"),
    USER_ASSIGNED_FOR_THIS_TIME("Uzytkownik jest juz zapisany na prelekcje o tej godzinie"),
    USER_EMAIL_EMPTY("Email nie może być pusty"),
    USER_LOGIN_EMPTY("Login nie może być pusty");

    private final String message;

    UserError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
