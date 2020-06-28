package com.rodrigopeleias.bookstoremanager.users.exception;

import javax.persistence.EntityExistsException;

public class UserAlreadyExistsException extends EntityExistsException {

    public UserAlreadyExistsException(String email, String username) {
        super(String.format("User with email %s or username %s already exists!", email, username));
    }
}
