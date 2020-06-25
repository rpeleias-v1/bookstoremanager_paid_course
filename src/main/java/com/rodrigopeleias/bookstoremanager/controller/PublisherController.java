package com.rodrigopeleias.bookstoremanager.controller;

import com.rodrigopeleias.bookstoremanager.controller.docs.PublisherControllerDocs;
import com.rodrigopeleias.bookstoremanager.dto.PublisherDTO;
import com.rodrigopeleias.bookstoremanager.exception.PublisherAlreadyExistsException;
import com.rodrigopeleias.bookstoremanager.service.PublisherService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/publishers")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PublisherController implements PublisherControllerDocs {

    private final PublisherService publisherService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PublisherDTO create(@RequestBody @Valid PublisherDTO publisherDTO) throws PublisherAlreadyExistsException {
        return publisherService.create(publisherDTO);
    }
}
