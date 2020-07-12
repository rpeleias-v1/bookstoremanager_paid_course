package com.rodrigopeleias.bookstoremanager.books.service;

import com.rodrigopeleias.bookstoremanager.authors.entity.Author;
import com.rodrigopeleias.bookstoremanager.authors.service.AuthorService;
import com.rodrigopeleias.bookstoremanager.books.builder.BookRequestDTOBuilder;
import com.rodrigopeleias.bookstoremanager.books.builder.BookResponseDTOBuilder;
import com.rodrigopeleias.bookstoremanager.books.dto.BookRequestDTO;
import com.rodrigopeleias.bookstoremanager.books.dto.BookResponseDTO;
import com.rodrigopeleias.bookstoremanager.books.entity.Book;
import com.rodrigopeleias.bookstoremanager.books.exception.BookAlreadyExistsException;
import com.rodrigopeleias.bookstoremanager.books.exception.BookNotFoundException;
import com.rodrigopeleias.bookstoremanager.books.mapper.BookMapper;
import com.rodrigopeleias.bookstoremanager.books.repository.BookRepository;
import com.rodrigopeleias.bookstoremanager.publishers.entity.Publisher;
import com.rodrigopeleias.bookstoremanager.publishers.service.PublisherService;
import com.rodrigopeleias.bookstoremanager.users.dto.AuthenticatedUser;
import com.rodrigopeleias.bookstoremanager.users.entity.User;
import com.rodrigopeleias.bookstoremanager.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorService authorService;

    @Mock
    private PublisherService publisherService;

    @Mock
    private UserService userService;

    @InjectMocks
    private BookService bookService;

    private BookRequestDTOBuilder bookRequestDTOBuilder;

    private BookResponseDTOBuilder bookResponseDTOBuilder;

    private AuthenticatedUser authenticatedUser;

    @BeforeEach
    void setUp() {
        bookRequestDTOBuilder = BookRequestDTOBuilder.builder().build();
        bookResponseDTOBuilder = BookResponseDTOBuilder.builder().build();
        authenticatedUser = new AuthenticatedUser("rodrigo", "123456", "ADMIN");
    }

    @Test
    void whenNewBookIsInformedThenItShouldBeCreated() {
        BookRequestDTO expectedBookToCreateDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedCreatedBookDTO = bookResponseDTOBuilder.buildBookResponseDTO();
        Book expectedCreatedBook = bookMapper.toModel(expectedCreatedBookDTO);

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findByNameAndIsbnAndUser(
                eq(expectedBookToCreateDTO.getName()),
                eq(expectedBookToCreateDTO.getIsbn()),
                any(User.class)))
                .thenReturn(Optional.empty());
        when(authorService.verifyAndGetIfExists(expectedBookToCreateDTO.getAuthorId())).thenReturn(new Author());
        when(publisherService.verifyAndGetIfExists(expectedBookToCreateDTO.getPublisherId())).thenReturn(new Publisher());
        when(bookRepository.save(any(Book.class))).thenReturn(expectedCreatedBook);

        BookResponseDTO createdBookResponseDTO = bookService.create(authenticatedUser, expectedBookToCreateDTO);

        assertThat(createdBookResponseDTO, is(equalTo(expectedCreatedBookDTO)));
    }

    @Test
    void whenExistingBookIsInformedThenAnExceptionShouldBeThrown() {
        BookRequestDTO expectedBookToCreateDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        Book expectedBookToCreate = bookMapper.toModel(expectedBookToCreateDTO);

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findByNameAndIsbnAndUser(
                eq(expectedBookToCreateDTO.getName()),
                eq(expectedBookToCreateDTO.getIsbn()),
                any(User.class)))
                .thenReturn(Optional.of(expectedBookToCreate));

        assertThrows(BookAlreadyExistsException.class, () -> bookService.create(authenticatedUser, expectedBookToCreateDTO));
    }

    @Test
    void whenExistingBookAndUserIsInformedThenABookShouldBeReturned() {
        BookRequestDTO expectedBookToFindDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedFoundBookDTO = bookResponseDTOBuilder.buildBookResponseDTO();
        Book expectedFoundBook = bookMapper.toModel(expectedFoundBookDTO);

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findByIdAndUser(
                eq(expectedBookToFindDTO.getId()),
                any(User.class)))
                .thenReturn(Optional.of(expectedFoundBook));

        BookResponseDTO foundBookDTO = bookService.findByIdAndUser(authenticatedUser, expectedBookToFindDTO.getId());

        assertThat(foundBookDTO, is(equalTo(expectedFoundBookDTO)));
    }

    @Test
    void whenListBookWithUserIsInformedIsCalledhenBooksShouldBeReturned() {
        BookResponseDTO expectedFoundBookDTO = bookResponseDTOBuilder.buildBookResponseDTO();
        Book expectedFoundBook = bookMapper.toModel(expectedFoundBookDTO);

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findAllByUser(any(User.class)))
                .thenReturn(Collections.singletonList(expectedFoundBook));

        List<BookResponseDTO> bookResponseDTOListByUser = bookService.findAllByUser(authenticatedUser);

        assertThat(bookResponseDTOListByUser.size(), is(1));
        assertThat(bookResponseDTOListByUser.get(0), is(equalTo(expectedFoundBookDTO)));
    }

    @Test
    void whenListBookWithUserIsInformedIsCalledThenEmptyListShouldBeReturned() {

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findAllByUser(any(User.class)))
                .thenReturn(Collections.emptyList());

        List<BookResponseDTO> bookResponseDTOListByUser = bookService.findAllByUser(authenticatedUser);

        assertThat(bookResponseDTOListByUser.size(), is(0));
    }

    @Test
    void whenNotExistingBookAndUserIsInformedThenAndExceptionShouldBeThrown() {
        BookRequestDTO expectedBookToFindDTO = bookRequestDTOBuilder.buildRequestBookDTO();

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findByIdAndUser(
                eq(expectedBookToFindDTO.getId()),
                any(User.class)))
                .thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.findByIdAndUser(authenticatedUser, expectedBookToFindDTO.getId()));
    }
}
