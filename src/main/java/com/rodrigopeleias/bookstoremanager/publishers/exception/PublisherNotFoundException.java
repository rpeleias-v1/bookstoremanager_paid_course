package com.rodrigopeleias.bookstoremanager.publishers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PublisherNotFoundException extends Exception{

    public PublisherNotFoundException(String name, String code) {
        super(String.format("Publisher with name %s or code %%s not exists!", name, code));
    }
}
