package com.rodrigopeleias.bookstoremanager.books.exception;

import javax.persistence.EntityExistsException;

public class BookAlreadyExistsException extends EntityExistsException {

    public BookAlreadyExistsException(String name, String isbn, String username) {
        super(String.format("Book with name %s, ISBN %s for user " +
                "%s already registered!", name, isbn, username));
    }
}
