package com.rodrigopeleias.bookstoremanager.publishers.service;

import com.rodrigopeleias.bookstoremanager.publishers.dto.PublisherDTO;
import com.rodrigopeleias.bookstoremanager.publishers.entity.Publisher;
import com.rodrigopeleias.bookstoremanager.publishers.exception.PublisherAlreadyExistsException;
import com.rodrigopeleias.bookstoremanager.publishers.exception.PublisherNotFoundException;
import com.rodrigopeleias.bookstoremanager.publishers.mapper.PublisherMapper;
import com.rodrigopeleias.bookstoremanager.publishers.repository.PublisherRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PublisherService {

    private final static PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    private final PublisherRepository publisherRepository;

    public PublisherDTO create(PublisherDTO publisherDTO) {
        verifyIfExists(publisherDTO.getName(), publisherDTO.getCode());
        Publisher publisherToCreate = publisherMapper.toModel(publisherDTO);
        Publisher createdPublisher = publisherRepository.save(publisherToCreate);
        return publisherMapper.toDTO(createdPublisher);
    }

    public Publisher updateIfExists(PublisherDTO publisherDTO) {
        Optional<Publisher> optExistingPublisher = publisherRepository.findByNameOrCode(publisherDTO.getName(), publisherDTO.getCode());
        if (optExistingPublisher.isPresent()) {
            return update(publisherDTO, optExistingPublisher.get());
        }
        PublisherDTO createdPubisherDTO = create(publisherDTO);
        return publisherMapper.toModel(createdPubisherDTO);
    }

    public List<PublisherDTO> findAll() {
        return publisherRepository.findAll()
                .stream()
                .map(publisherMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PublisherDTO findById(Long id) {
        return publisherRepository.findById(id)
                .map(publisherMapper::toDTO)
                .orElseThrow(() -> new PublisherNotFoundException(id));
    }

    public void delete(Long id) {
        verifyAndGetIfExists(id);
        publisherRepository.deleteById(id);
    }

    public Publisher verifyAndGetIfExists(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new PublisherNotFoundException(id));
    }

    private Publisher update(PublisherDTO publisherDTO, Publisher existingPublisher) {
        existingPublisher.setName(publisherDTO.getName());
        existingPublisher.setCode(publisherDTO.getCode());
        existingPublisher.setFoundationDate(publisherDTO.getFoundationDate());
        return existingPublisher;
    }

    private void verifyIfExists(String name, String code) {
        Optional<Publisher> duplicatedPublisher = publisherRepository.findByNameOrCode(name, code);
        if (duplicatedPublisher.isPresent()) {
            throw new PublisherAlreadyExistsException(name, code);
        }
    }
}
