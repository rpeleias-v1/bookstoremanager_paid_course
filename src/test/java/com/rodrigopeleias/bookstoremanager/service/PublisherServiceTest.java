package com.rodrigopeleias.bookstoremanager.service;

import com.rodrigopeleias.bookstoremanager.builder.AuthorDTOBuilder;
import com.rodrigopeleias.bookstoremanager.builder.PublisherDTOBuilder;
import com.rodrigopeleias.bookstoremanager.dto.AuthorDTO;
import com.rodrigopeleias.bookstoremanager.dto.PublisherDTO;
import com.rodrigopeleias.bookstoremanager.entity.Author;
import com.rodrigopeleias.bookstoremanager.entity.Publisher;
import com.rodrigopeleias.bookstoremanager.exception.AuthorAlreadyExistsException;
import com.rodrigopeleias.bookstoremanager.exception.PublisherAlreadyExistsException;
import com.rodrigopeleias.bookstoremanager.exception.PublisherNotFoundException;
import com.rodrigopeleias.bookstoremanager.mapper.PublisherMapper;
import com.rodrigopeleias.bookstoremanager.repository.PublisherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PublisherServiceTest {

    private final PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherService publisherService;

    @Test
    void whenNewPublisherIsInformedThenItShouldBeCreated() throws PublisherAlreadyExistsException {
        PublisherDTO expectedPublisherToCreateDTO = PublisherDTOBuilder.builder().build().buildPublisherDTO();
        Publisher expectedPublisherToCreate = publisherMapper.toModel(expectedPublisherToCreateDTO);

        when(publisherRepository.findByNameOrCode(expectedPublisherToCreateDTO.getName(), expectedPublisherToCreateDTO.getCode()))
                .thenReturn(Optional.empty());
        when(publisherRepository.save(expectedPublisherToCreate)).thenReturn(expectedPublisherToCreate);

        PublisherDTO createdPublisherDTO = publisherService.create(expectedPublisherToCreateDTO);

        assertThat(createdPublisherDTO, is(equalTo(expectedPublisherToCreateDTO)));
    }

    @Test
    void whenExistingPublisherIsInformedThenThrowsAnException() {
        PublisherDTO expectedPublisherToFindDTO = PublisherDTOBuilder.builder().build().buildPublisherDTO();
        Publisher expectedPublisherToFind = publisherMapper.toModel(expectedPublisherToFindDTO);

        when(publisherRepository.findByNameOrCode(expectedPublisherToFindDTO.getName(), expectedPublisherToFindDTO.getCode()))
                .thenReturn(Optional.of(expectedPublisherToFind));

        assertThrows(PublisherAlreadyExistsException.class, () -> publisherService.create(expectedPublisherToFindDTO));
    }
}
