package com.rodrigopeleias.bookstoremanager.books.service;

import com.rodrigopeleias.bookstoremanager.authors.entity.Author;
import com.rodrigopeleias.bookstoremanager.authors.exception.AuthorNotFoundException;
import com.rodrigopeleias.bookstoremanager.authors.repository.AuthorRepository;
import com.rodrigopeleias.bookstoremanager.books.dto.BookRequestDTO;
import com.rodrigopeleias.bookstoremanager.books.dto.BookResponseDTO;
import com.rodrigopeleias.bookstoremanager.books.entity.Book;
import com.rodrigopeleias.bookstoremanager.books.exception.BookAlreadyExistsException;
import com.rodrigopeleias.bookstoremanager.books.mapper.BookMapper;
import com.rodrigopeleias.bookstoremanager.books.repository.BookRepository;
import com.rodrigopeleias.bookstoremanager.publishers.entity.Publisher;
import com.rodrigopeleias.bookstoremanager.publishers.exception.PublisherNotFoundException;
import com.rodrigopeleias.bookstoremanager.publishers.repository.PublisherRepository;
import com.rodrigopeleias.bookstoremanager.users.dto.AuthenticatedUser;
import com.rodrigopeleias.bookstoremanager.users.entity.User;
import com.rodrigopeleias.bookstoremanager.users.exception.UserNotFoundException;
import com.rodrigopeleias.bookstoremanager.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    private final AuthorRepository authorRepository;

    private final PublisherRepository publisherRepository;

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    public BookResponseDTO create(AuthenticatedUser authenticatedUser, BookRequestDTO bookRequestDTO) {
        User foundAuthenticatedUser = verifyAndGetUserIfExists(authenticatedUser);
        verifyIfIsAlreadyRegistered(bookRequestDTO, foundAuthenticatedUser);
        Author foundAuthor = verifyIfAuthorExists(bookRequestDTO);
        Publisher foundPublisher = verifyIfPublisherExists(bookRequestDTO);

        Book bookToSave = bookMapper.toModel(bookRequestDTO);
        bookToSave.setUser(foundAuthenticatedUser);
        bookToSave.setAuthor(foundAuthor);
        bookToSave.setPublisher(foundPublisher);

        Book savedBook = bookRepository.save(bookToSave);
        return bookMapper.toDTO(savedBook);
    }

    private User verifyAndGetUserIfExists(AuthenticatedUser authenticatedUser) {
        return userRepository.findByUsername(authenticatedUser.getUsername())
                .orElseThrow(() -> new UserNotFoundException(authenticatedUser.getUsername()));
    }

    private Author verifyIfAuthorExists(BookRequestDTO bookRequestDTO) {
        return authorRepository.findById(bookRequestDTO.getAuthorId())
                .orElseThrow(() -> new AuthorNotFoundException(bookRequestDTO.getAuthorId()));
    }

    private void verifyIfIsAlreadyRegistered(BookRequestDTO bookRequestDTO, User foundAuthenticatedUser) {
        bookRepository.findByNameAndIsbnAndUser(bookRequestDTO.getName(), bookRequestDTO.getIsbn(), foundAuthenticatedUser)
                .ifPresent(duplicatedBook -> {
                    throw new BookAlreadyExistsException(bookRequestDTO.getName(), bookRequestDTO.getIsbn(), foundAuthenticatedUser.getName());
                });
    }

    private Publisher verifyIfPublisherExists(BookRequestDTO bookRequestDTO) {
        return publisherRepository.findById(bookRequestDTO.getPublisherId())
                .orElseThrow(() -> new PublisherNotFoundException(bookRequestDTO.getPublisherId()));
    }
}
