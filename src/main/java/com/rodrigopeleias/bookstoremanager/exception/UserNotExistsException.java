package com.rodrigopeleias.bookstoremanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotExistsException extends Exception{

    public UserNotExistsException(Long id) {
        super(String.format("User with id %s or username not exists!", id));
    }
}
