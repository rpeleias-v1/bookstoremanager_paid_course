package com.rodrigopeleias.bookstoremanager.authors.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AuthorNotFoundException extends Exception{

    public AuthorNotFoundException(String name) {
        super(String.format("Author with name %s not exists!", name));
    }

    public AuthorNotFoundException(Long id) {
        super(String.format("Author with id %s not exists!", id));
    }
}
