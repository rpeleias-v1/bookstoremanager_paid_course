package com.rodrigopeleias.bookstoremanager.publishers.service;

import com.rodrigopeleias.bookstoremanager.publishers.builder.PublisherDTOBuilder;
import com.rodrigopeleias.bookstoremanager.publishers.dto.PublisherDTO;
import com.rodrigopeleias.bookstoremanager.publishers.entity.Publisher;
import com.rodrigopeleias.bookstoremanager.publishers.exception.PublisherAlreadyExistsException;
import com.rodrigopeleias.bookstoremanager.publishers.exception.PublisherNotFoundException;
import com.rodrigopeleias.bookstoremanager.publishers.mapper.PublisherMapper;
import com.rodrigopeleias.bookstoremanager.publishers.repository.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PublisherServiceTest {

    private final PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherService publisherService;

    private PublisherDTOBuilder publisherDtoBuilder;

    @BeforeEach
    void setUp() {
        publisherDtoBuilder = PublisherDTOBuilder.builder().build();
    }

    @Test
    void whenNewPublisherIsInformedThenItShouldBeCreated() {
        PublisherDTO expectedPublisherToCreateDTO = publisherDtoBuilder.buildPublisherDTO();
        Publisher expectedPublisherToCreate = publisherMapper.toModel(expectedPublisherToCreateDTO);

        when(publisherRepository.findByNameOrCode(expectedPublisherToCreateDTO.getName(), expectedPublisherToCreateDTO.getCode()))
                .thenReturn(Optional.empty());
        when(publisherRepository.save(expectedPublisherToCreate)).thenReturn(expectedPublisherToCreate);

        PublisherDTO createdPublisherDTO = publisherService.create(expectedPublisherToCreateDTO);

        assertThat(createdPublisherDTO, is(equalTo(expectedPublisherToCreateDTO)));
    }

    @Test
    void whenExistingPublisherIsInformedThenAnExceptionShouldBeThrown() {
        PublisherDTO expectedPublisherToFindDTO = publisherDtoBuilder.buildPublisherDTO();
        Publisher expectedPublisherToFind = publisherMapper.toModel(expectedPublisherToFindDTO);

        when(publisherRepository.findByNameOrCode(expectedPublisherToFindDTO.getName(), expectedPublisherToFindDTO.getCode()))
                .thenReturn(Optional.of(expectedPublisherToFind));

        assertThrows(PublisherAlreadyExistsException.class, () -> publisherService.create(expectedPublisherToFindDTO));
    }

    @Test
    void whenListPublishersIsCalledThenItShouldBeReturned() {
        PublisherDTO expectedPublisherToFindDTO = publisherDtoBuilder.buildPublisherDTO();
        Publisher expectedPublisherToFind = publisherMapper.toModel(expectedPublisherToFindDTO);

        when(publisherRepository.findAll()).thenReturn(Collections.singletonList(expectedPublisherToFind));

        List<PublisherDTO> foundPublishersDTO = publisherService.findAll();

        assertThat(foundPublishersDTO.size(), is(1));
        assertThat(foundPublishersDTO.get(0), is(equalTo(expectedPublisherToFindDTO)));
    }

    @Test
    void whenListPublishersIsCalledThenAndEmptyListShouldBeReturned() {
        when(publisherRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        List<PublisherDTO> foundPublishersDTO = publisherService.findAll();

        assertThat(foundPublishersDTO.size(), is(0));
    }

    @Test
    void whenValidIdIsGivenThenAPublisherShouldBeReturned() {
        PublisherDTO expectedPublisherFindDTO = publisherDtoBuilder.buildPublisherDTO();
        Publisher expectedPublisherToFind = publisherMapper.toModel(expectedPublisherFindDTO);

        when(publisherRepository.findById(expectedPublisherFindDTO.getId())).thenReturn(Optional.of(expectedPublisherToFind));

        PublisherDTO foundPublisherDTO = publisherService.findById(expectedPublisherFindDTO.getId());

        assertThat(expectedPublisherFindDTO, is(equalTo(foundPublisherDTO)));
    }

    @Test
    void whenInvalidIdIsGivenThenAnExceptionShouldBeThrown() {
        PublisherDTO expectedPublisherFindDTO = publisherDtoBuilder.buildPublisherDTO();

        when(publisherRepository.findById(expectedPublisherFindDTO.getId())).thenReturn(Optional.empty());

        assertThrows(PublisherNotFoundException.class, () -> publisherService.findById(expectedPublisherFindDTO.getId()));
    }

    @Test
    void whenExistingPublisherIdIsGivenThenItShouldBeReturned() {
        PublisherDTO expectedFoundPublisherDTO = publisherDtoBuilder.buildPublisherDTO();
        Publisher expectedFoundPublisher = publisherMapper.toModel(expectedFoundPublisherDTO);

        when(publisherRepository.findById(expectedFoundPublisherDTO.getId()))
                .thenReturn(Optional.of(expectedFoundPublisher));

        Publisher foundPublisher = publisherService.verifyAndGetIfExists(expectedFoundPublisherDTO.getId());

        assertThat(foundPublisher, is(equalTo(expectedFoundPublisher)));
    }

    @Test
    void whenNotExistingAuthorIdIsGivenThenAndExceptionShouldBeThrown() {
        PublisherDTO expectedFoundPublisherDTO = publisherDtoBuilder.buildPublisherDTO();
        long invalidId = 1L;

        when(publisherRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(PublisherNotFoundException.class, () -> publisherService.verifyAndGetIfExists(expectedFoundPublisherDTO.getId()));
    }

    @Test
    void whenValidPublisherIdIsGivenTheItShouldBeDeleted() {
        PublisherDTO expectedPublisherToDeleteDTO = PublisherDTOBuilder.builder().build().buildPublisherDTO();
        Publisher expectedDeletedPublisher = publisherMapper.toModel(expectedPublisherToDeleteDTO);

        Long expectedDeletedPublisherId = expectedPublisherToDeleteDTO.getId();
        doNothing().when(publisherRepository).deleteById(expectedDeletedPublisherId);
        when(publisherRepository.findById(expectedDeletedPublisherId)).thenReturn(Optional.of(expectedDeletedPublisher));

        publisherService.delete(expectedPublisherToDeleteDTO.getId());

        verify(publisherRepository, times(1)).findById(expectedDeletedPublisherId);
        verify(publisherRepository, times(1)).deleteById(expectedDeletedPublisherId);
    }

    @Test
    void whenInvalidPublisherIsGivenThenAnExceptionShouldBeThrown() {
        Long expectedNotFoundPublisherId = 2L;

        when(publisherRepository.findById(expectedNotFoundPublisherId)).thenReturn(Optional.empty());

        assertThrows(PublisherNotFoundException.class, () -> publisherService.delete(expectedNotFoundPublisherId));
    }

}
