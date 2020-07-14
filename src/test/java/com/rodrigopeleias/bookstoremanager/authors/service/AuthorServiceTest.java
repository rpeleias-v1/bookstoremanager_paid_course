package com.rodrigopeleias.bookstoremanager.authors.service;

import com.rodrigopeleias.bookstoremanager.authors.builder.AuthorDTOBuilder;
import com.rodrigopeleias.bookstoremanager.authors.dto.AuthorDTO;
import com.rodrigopeleias.bookstoremanager.authors.entity.Author;
import com.rodrigopeleias.bookstoremanager.authors.exception.AuthorAlreadyExistsException;
import com.rodrigopeleias.bookstoremanager.authors.exception.AuthorNotFoundException;
import com.rodrigopeleias.bookstoremanager.authors.mapper.AuthorMapper;
import com.rodrigopeleias.bookstoremanager.authors.repository.AuthorRepository;
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
public class AuthorServiceTest {

    private final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private AuthorDTOBuilder authorDtoBuilder;

    @BeforeEach
    void setUp() {
        authorDtoBuilder = AuthorDTOBuilder.builder().build();
    }

    @Test
    void whenNewAuthorIsInformedThenItShouldBeCreated() {
        AuthorDTO expectedAuthorToCreateDTO = authorDtoBuilder.buildAuthorDTO();
        Author expectedCreatedAuthor = authorMapper.toModel(expectedAuthorToCreateDTO);

        when(authorRepository.save(expectedCreatedAuthor)).thenReturn(expectedCreatedAuthor);
        when(authorRepository.findByName(expectedAuthorToCreateDTO.getName())).thenReturn(Optional.empty());

        AuthorDTO createdAuthorDTO = authorService.create(expectedAuthorToCreateDTO);

        assertThat(createdAuthorDTO, is(equalTo(expectedAuthorToCreateDTO)));
    }

    @Test
    void whenExistingAuthorIsInformedThenAnExceptionShouldBeThrown() {
        AuthorDTO expectedAuthorToCreateDTO = authorDtoBuilder.buildAuthorDTO();
        Author expectedDuplicatedAuthor = authorMapper.toModel(expectedAuthorToCreateDTO);

        when(authorRepository.findByName(expectedAuthorToCreateDTO.getName())).thenReturn(Optional.of(expectedDuplicatedAuthor));

        assertThrows(AuthorAlreadyExistsException.class, () -> authorService.create(expectedAuthorToCreateDTO));
    }

    @Test
    void whenValidNameIsGivenThenAnAuthorShouldBeReturned() {
        AuthorDTO expectedFoundAuthorDTO = authorDtoBuilder.buildAuthorDTO();
        Author expectedFoundAuthor = authorMapper.toModel(expectedFoundAuthorDTO);

        when(authorRepository.findByName(expectedFoundAuthorDTO.getName())).thenReturn(Optional.of(expectedFoundAuthor));

        AuthorDTO foundAuthor = authorService.findByName(expectedFoundAuthorDTO.getName());

        assertThat(foundAuthor, is(equalTo(expectedFoundAuthorDTO)));
    }

    @Test
    void whenInvalidNameIsGivenThenAnExceptionShouldBeThrown() {
        AuthorDTO expectedFoundAuthorDTO = authorDtoBuilder.buildAuthorDTO();

        when(authorRepository.findByName(expectedFoundAuthorDTO.getName())).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> authorService.findByName(expectedFoundAuthorDTO.getName()));
    }

    @Test
    void whenListAuthorsIsCalledThenItShouldBeReturned() {
        AuthorDTO expectedFoundAuthorDTO = AuthorDTOBuilder.builder().build().buildAuthorDTO();
        Author expectedFoundAuthor = authorMapper.toModel(expectedFoundAuthorDTO);

        when(authorRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundAuthor));

        List<AuthorDTO> foundAuthorsDTO = authorService.findAll();

        assertThat(foundAuthorsDTO.size(), is(1));
        assertThat(foundAuthorsDTO.get(0), is(equalTo(expectedFoundAuthorDTO)));
    }

    @Test
    void whenListAuthorsIsCalledThenAndEmptyListShouldBeReturned() {
        AuthorDTO expectedFoundAuthorDTO = authorDtoBuilder.buildAuthorDTO();

        when(authorRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        List<AuthorDTO> foundAuthorsDTO = authorService.findAll();

        assertThat(foundAuthorsDTO.size(), is(0));
    }

    @Test
    void whenExistingAuthorIdIsGivenThenAuthorEntityShouldBeReturned() {
        AuthorDTO expectedFoundAuthorDTO = authorDtoBuilder.buildAuthorDTO();
        Author expectedFoundAuthor = authorMapper.toModel(expectedFoundAuthorDTO);

        when(authorRepository.findById(expectedFoundAuthorDTO.getId()))
                .thenReturn(Optional.of(expectedFoundAuthor));

        Author foundAuthor = authorService.verifyAndGetIfExists(expectedFoundAuthorDTO.getId());

        assertThat(foundAuthor, is(equalTo(expectedFoundAuthor)));
    }

    @Test
    void whenNotExistingAuthorIdIsGivenThenAndExceptionShouldBeThrown() {
        AuthorDTO expectedFoundAuthorDTO = authorDtoBuilder.buildAuthorDTO();
        long invalidId = 1L;

        when(authorRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> authorService.verifyAndGetIfExists(expectedFoundAuthorDTO.getId()));
    }

    @Test
    void whenValidAuthorIdIsGivenThenItShouldBeReturned() {
        AuthorDTO expectedDeletedAuthorDTO = authorDtoBuilder.buildAuthorDTO();
        Author expectedDeletedAuthor = authorMapper.toModel(expectedDeletedAuthorDTO);

        Long expectedDeletedAuthorId = expectedDeletedAuthorDTO.getId();
        doNothing().when(authorRepository).deleteById(expectedDeletedAuthorId);
        when(authorRepository.findById(expectedDeletedAuthorId)).thenReturn(Optional.of(expectedDeletedAuthor));

        authorService.delete(expectedDeletedAuthor.getId());

        verify(authorRepository, times(1)).findById(expectedDeletedAuthorId);
        verify(authorRepository, times(1)).deleteById(expectedDeletedAuthorId);
    }

    @Test
    void whenInvalidAuthorIsGivenThenAnExceptionShouldBeThrown() {
        Long expectedNotFoundAuthorId = 2L;

        when(authorRepository.findById(expectedNotFoundAuthorId)).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> authorService.delete(expectedNotFoundAuthorId));
    }
}
