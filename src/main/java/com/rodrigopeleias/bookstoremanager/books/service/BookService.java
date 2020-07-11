package com.rodrigopeleias.bookstoremanager.books.service;

import com.rodrigopeleias.bookstoremanager.authors.entity.Author;
import com.rodrigopeleias.bookstoremanager.authors.service.AuthorService;
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
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

    private final BookRepository bookRepository;

    private final UserService userService;

    private final AuthorService authorService;

    private final PublisherService publisherService;

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    public BookResponseDTO create(AuthenticatedUser authenticatedUser, BookRequestDTO bookRequestDTO) {
        User foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        verifyIfIsAlreadyRegistered(bookRequestDTO, foundAuthenticatedUser);
        Author foundAuthor = authorService.verifyAndGetIfExists(bookRequestDTO.getAuthorId());
        Publisher foundPublisher = publisherService.verifyAndGetIfExists(bookRequestDTO.getPublisherId());

        Book bookToSave = bookMapper.toModel(bookRequestDTO);
        bookToSave.setUser(foundAuthenticatedUser);
        bookToSave.setAuthor(foundAuthor);
        bookToSave.setPublisher(foundPublisher);
        Book savedBook = bookRepository.save(bookToSave);
        return bookMapper.toDTO(savedBook);
    }

    public BookResponseDTO findByIdAndUser(AuthenticatedUser authenticatedUser, Long bookId) {
        User foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        return bookRepository.findByIdAndUser(bookId, foundAuthenticatedUser)
                .map(bookMapper::toDTO)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    private void verifyIfIsAlreadyRegistered(BookRequestDTO bookRequestDTO, User foundAuthenticatedUser) {
        bookRepository.findByNameAndIsbnAndUser(bookRequestDTO.getName(), bookRequestDTO.getIsbn(), foundAuthenticatedUser)
                .ifPresent(duplicatedBook -> {
                    throw new BookAlreadyExistsException(bookRequestDTO.getName(), bookRequestDTO.getIsbn(), foundAuthenticatedUser.getName());
                });
    }
}
