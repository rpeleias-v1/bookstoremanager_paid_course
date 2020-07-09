package com.rodrigopeleias.bookstoremanager.books.service;

import com.rodrigopeleias.bookstoremanager.authors.entity.Author;
import com.rodrigopeleias.bookstoremanager.authors.service.AuthorService;
import com.rodrigopeleias.bookstoremanager.books.dto.BookDTO;
import com.rodrigopeleias.bookstoremanager.books.entity.Book;
import com.rodrigopeleias.bookstoremanager.books.exception.BookAlreadyExistsException;
import com.rodrigopeleias.bookstoremanager.books.mapper.BookMapper;
import com.rodrigopeleias.bookstoremanager.books.repository.BookRepository;
import com.rodrigopeleias.bookstoremanager.publishers.entity.Publisher;
import com.rodrigopeleias.bookstoremanager.publishers.service.PublisherService;
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

    private final AuthorService authorService;

    private final PublisherService publisherService;

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    public BookDTO create(AuthenticatedUser authenticatedUser, BookDTO bookDTO) {
        User foundAuthenticatedUser = verifyAndGetUserIfExists(authenticatedUser);
        verifyIfIsAlreadyRegistered(bookDTO, foundAuthenticatedUser);
        Author createdOrUpdatedAuthor = authorService.createOrUpdateIfExists(bookDTO.getAuthor());
        Publisher createdOrUpdatedPublisher = publisherService.updateIfExists(bookDTO.getPublisher());

        Book bookToSave = bookMapper.toModel(bookDTO);
        bookToSave.setUser(foundAuthenticatedUser);
        bookToSave.setAuthor(createdOrUpdatedAuthor);
        bookToSave.setPublisher(createdOrUpdatedPublisher);

        Book savedBook = bookRepository.save(bookToSave);
        return bookMapper.toDTO(savedBook);
    }

    private User verifyAndGetUserIfExists(AuthenticatedUser authenticatedUser) {
        return userRepository.findByUsername(authenticatedUser.getUsername())
                .orElseThrow(() -> new UserNotFoundException(authenticatedUser.getUsername()));
    }

    private void verifyIfIsAlreadyRegistered(BookDTO bookDTO, User foundAuthenticatedUser) {
        bookRepository.findByNameAndIsbnAndUser(bookDTO.getName(), bookDTO.getIsbn(), foundAuthenticatedUser)
                .ifPresent(duplicatedBook -> {
                    throw new BookAlreadyExistsException(bookDTO.getName(), bookDTO.getIsbn(), foundAuthenticatedUser.getName());
                });
    }
}
