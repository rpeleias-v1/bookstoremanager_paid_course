package com.rodrigopeleias.bookstoremanager.books.exception;

import javax.persistence.EntityNotFoundException;

public class BookNotFoundException extends EntityNotFoundException {

    public BookNotFoundException(String name) {
        super(String.format("Book with name %s not exists!", name));
    }

    public BookNotFoundException(Long id) {
        super(String.format("Book with id %s not exists!", id));
    }
}
