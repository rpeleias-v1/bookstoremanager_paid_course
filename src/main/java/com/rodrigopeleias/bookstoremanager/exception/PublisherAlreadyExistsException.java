package com.rodrigopeleias.bookstoremanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PublisherAlreadyExistsException extends Exception{

    public PublisherAlreadyExistsException(String name, String code) {
        super(String.format("Publisher with name %s or code %s already exists!", name, code));
    }
}
