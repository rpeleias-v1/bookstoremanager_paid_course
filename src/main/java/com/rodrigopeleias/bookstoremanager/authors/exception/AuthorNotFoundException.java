package com.rodrigopeleias.bookstoremanager.authors.exception;

import javax.persistence.EntityNotFoundException;

public class AuthorNotFoundException extends EntityNotFoundException {

    public AuthorNotFoundException(String name) {
        super(String.format("Author with name %s not exists!", name));
    }

    public AuthorNotFoundException(Long id) {
        super(String.format("Author with id %s not exists!", id));
    }
}
