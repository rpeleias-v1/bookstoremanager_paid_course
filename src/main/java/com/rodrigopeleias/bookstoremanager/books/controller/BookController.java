package com.rodrigopeleias.bookstoremanager.books.controller;

import com.rodrigopeleias.bookstoremanager.books.controller.docs.BookControllerDocs;
import com.rodrigopeleias.bookstoremanager.books.dto.BookRequestDTO;
import com.rodrigopeleias.bookstoremanager.books.dto.BookResponseDTO;
import com.rodrigopeleias.bookstoremanager.books.service.BookService;
import com.rodrigopeleias.bookstoremanager.users.dto.AuthenticatedUser;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/books")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookController implements BookControllerDocs {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponseDTO create(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, @RequestBody @Valid BookRequestDTO bookRequestDTO) {
        return bookService.create(authenticatedUser, bookRequestDTO);
    }

    @GetMapping("/{id}")
    public BookResponseDTO findByIdAndUser(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, @PathVariable Long id) {
        return bookService.findByIdAndUser(authenticatedUser, id);
    }
}
