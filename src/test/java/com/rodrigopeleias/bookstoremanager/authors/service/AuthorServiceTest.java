package com.rodrigopeleias.bookstoremanager.authors.service;

import com.rodrigopeleias.bookstoremanager.authors.builder.AuthorDTOBuilder;
import com.rodrigopeleias.bookstoremanager.authors.dto.AuthorDTO;
import com.rodrigopeleias.bookstoremanager.authors.entity.Author;
import com.rodrigopeleias.bookstoremanager.authors.exception.AuthorAlreadyExistsException;
import com.rodrigopeleias.bookstoremanager.authors.exception.AuthorNotFoundException;
import com.rodrigopeleias.bookstoremanager.authors.mapper.AuthorMapper;
import com.rodrigopeleias.bookstoremanager.authors.repository.AuthorRepository;
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

    @Test
    void whenNewAuthorIsInformedThenItShouldBeCreated() {
        AuthorDTO expectedAuthorToCreateDTO = AuthorDTOBuilder.builder().build().buildAuthorDTO();
        Author expectedCreatedAuthor = authorMapper.toModel(expectedAuthorToCreateDTO);

        when(authorRepository.save(expectedCreatedAuthor)).thenReturn(expectedCreatedAuthor);
        when(authorRepository.findByName(expectedAuthorToCreateDTO.getName())).thenReturn(Optional.empty());

        AuthorDTO createdAuthorDTO = authorService.create(expectedAuthorToCreateDTO);

        assertThat(createdAuthorDTO, is(equalTo(expectedAuthorToCreateDTO)));
    }

    @Test
    void whenExistingAuthorIsInformedThenThrowsAnException() {
        AuthorDTO expectedAuthorToCreateDTO = AuthorDTOBuilder.builder().build().buildAuthorDTO();
        Author expectedDuplicatedAuthor = authorMapper.toModel(expectedAuthorToCreateDTO);

        when(authorRepository.findByName(expectedAuthorToCreateDTO.getName())).thenReturn(Optional.of(expectedDuplicatedAuthor));

        assertThrows(AuthorAlreadyExistsException.class, () -> authorService.create(expectedAuthorToCreateDTO));
    }

    @Test
    void whenValidNameIsGivenThenReturnAnAuthor(){
        AuthorDTO expectedFoundAuthorDTO = AuthorDTOBuilder.builder().build().buildAuthorDTO();
        Author expectedFoundAuthor = authorMapper.toModel(expectedFoundAuthorDTO);

        when(authorRepository.findByName(expectedFoundAuthorDTO.getName())).thenReturn(Optional.of(expectedFoundAuthor));

        AuthorDTO foundAuthor = authorService.findByName(expectedFoundAuthorDTO.getName());

        assertThat(foundAuthor, is(equalTo(expectedFoundAuthorDTO)));
    }

    @Test
    void whenInvalidNameIsGivenThenThrowAnException() {
        AuthorDTO expectedFoundAuthorDTO = AuthorDTOBuilder.builder().build().buildAuthorDTO();

        when(authorRepository.findByName(expectedFoundAuthorDTO.getName())).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> authorService.findByName(expectedFoundAuthorDTO.getName()));
    }

    @Test
    void whenListAuthorsIsCalledThenReturnAuthors() {
        AuthorDTO expectedFoundAuthorDTO = AuthorDTOBuilder.builder().build().buildAuthorDTO();
        Author expectedFoundAuthor = authorMapper.toModel(expectedFoundAuthorDTO);

        when(authorRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundAuthor));

        List<AuthorDTO> foundAuthorsDTO = authorService.findAll();

        assertThat(foundAuthorsDTO.size(), is(1));
        assertThat(foundAuthorsDTO.get(0), is(equalTo(expectedFoundAuthorDTO)));
    }

    @Test
    void whenListAuthorsIsCalledThenReturnEmptyList() {
        AuthorDTO expectedFoundAuthorDTO = AuthorDTOBuilder.builder().build().buildAuthorDTO();
        Author expectedFoundAuthor = authorMapper.toModel(expectedFoundAuthorDTO);

        when(authorRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        List<AuthorDTO> foundAuthorsDTO = authorService.findAll();

        assertThat(foundAuthorsDTO.size(), is(0));
    }

    @Test
    void whenValidAuthorIdIsGivenTheDeleteThisAuthor(){
        AuthorDTO expectedDeletedAuthorDTO = AuthorDTOBuilder.builder().build().buildAuthorDTO();
        Author expectedDeletedAuthor = authorMapper.toModel(expectedDeletedAuthorDTO);

        Long expectedDeletedAuthorId = expectedDeletedAuthorDTO.getId();
        doNothing().when(authorRepository).deleteById(expectedDeletedAuthorId);
        when(authorRepository.findById(expectedDeletedAuthorId)).thenReturn(Optional.of(expectedDeletedAuthor));

        authorService.delete(expectedDeletedAuthor.getId());

        verify(authorRepository, times(1)).findById(expectedDeletedAuthorId);
        verify(authorRepository, times(1)).deleteById(expectedDeletedAuthorId);
    }

    @Test
    void whenInvalidAuthorIsGivenThenThrowAnException() {
        Long expectedNotFoundAuthorId = 2L;

        when(authorRepository.findById(expectedNotFoundAuthorId)).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> authorService.delete(expectedNotFoundAuthorId));
    }
}
