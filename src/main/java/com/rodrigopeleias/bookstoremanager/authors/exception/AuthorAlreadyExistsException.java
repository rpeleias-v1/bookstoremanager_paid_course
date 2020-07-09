package com.rodrigopeleias.bookstoremanager.authors.exception;

import javax.persistence.EntityExistsException;

public class AuthorAlreadyExistsException extends EntityExistsException {

    public AuthorAlreadyExistsException(String name, String code) {
        super(String.format("User with name %s or code %code already exists!", name, code));
    }

    public AuthorAlreadyExistsException(String name) {
        super(String.format("User with name %s already exists!", name));
    }
}
