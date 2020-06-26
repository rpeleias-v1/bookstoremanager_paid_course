package com.rodrigopeleias.bookstoremanager.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExistsException extends Exception{

    public UserAlreadyExistsException(String email, String username) {
        super(String.format("User with email %s or username %s already exists!", email, username));
    }
}
