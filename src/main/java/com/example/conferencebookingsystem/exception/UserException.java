package com.example.conferencebookingsystem.exception;

public class UserException extends RuntimeException {

    private UserError userError;

    public UserException(UserError userError) {
        this.userError = userError;
    }

    public UserError getUserError() {
        return userError;
    }

}
