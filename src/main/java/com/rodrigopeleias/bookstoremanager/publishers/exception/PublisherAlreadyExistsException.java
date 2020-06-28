package com.rodrigopeleias.bookstoremanager.publishers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityExistsException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PublisherAlreadyExistsException extends EntityExistsException {

    public PublisherAlreadyExistsException(String name, String code) {
        super(String.format("Publisher with name %s or code %s already exists!", name, code));
    }
}
