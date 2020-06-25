package com.rodrigopeleias.bookstoremanager.service;

import com.rodrigopeleias.bookstoremanager.dto.PublisherDTO;
import com.rodrigopeleias.bookstoremanager.entity.Publisher;
import com.rodrigopeleias.bookstoremanager.exception.PublisherAlreadyExistsException;
import com.rodrigopeleias.bookstoremanager.mapper.PublisherMapper;
import com.rodrigopeleias.bookstoremanager.repository.PublisherRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PublisherService {

    private final PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    private final PublisherRepository publisherRepository;

    public PublisherDTO create(PublisherDTO publisherDTO) throws PublisherAlreadyExistsException {
        verifyIfExists(publisherDTO.getName(), publisherDTO.getCode());
        Publisher publisherToCreate = publisherMapper.toModel(publisherDTO);
        Publisher createdPublisher = publisherRepository.save(publisherToCreate);
        return publisherMapper.toDTO(createdPublisher);
    }

    private void verifyIfExists(String name, String code) throws PublisherAlreadyExistsException {
        Optional<Publisher> duplicatedPublisher = publisherRepository.findByNameOrCode(name, code);
        if (duplicatedPublisher.isPresent()) {
            throw new PublisherAlreadyExistsException(name, code);
        }
    }

}
