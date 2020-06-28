package com.rodrigopeleias.bookstoremanager.publishers.exception;

import javax.persistence.EntityNotFoundException;

public class PublisherNotFoundException extends EntityNotFoundException {

    public PublisherNotFoundException(Long id) {
        super(String.format("Publisher with id %s not exists!", id));
    }
}
