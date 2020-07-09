package com.rodrigopeleias.bookstoremanager.books.exception;

import javax.persistence.EntityExistsException;

public class BookAlreadyExistsException extends EntityExistsException {

    public BookAlreadyExistsException(String name, String isbn, String username) {
        super(String.format("Book with name %name, ISBN %isbn for user " +
                "%username already registered!", name, isbn, username));
    }
}
