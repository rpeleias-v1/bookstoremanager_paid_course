package com.rodrigopeleias.bookstoremanager.authors.controller;

import com.rodrigopeleias.bookstoremanager.authors.controller.docs.AuthorControllerDocs;
import com.rodrigopeleias.bookstoremanager.authors.dto.AuthorDTO;
import com.rodrigopeleias.bookstoremanager.authors.exception.AuthorAlreadyExistsException;
import com.rodrigopeleias.bookstoremanager.authors.exception.AuthorNotFoundException;
import com.rodrigopeleias.bookstoremanager.authors.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorController implements AuthorControllerDocs {

    private final AuthorService authorService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO create(@RequestBody @Valid AuthorDTO authorDTO) throws AuthorAlreadyExistsException {
        return authorService.create(authorDTO);
    }

    @GetMapping("/{name}")
    public AuthorDTO findByName(@PathVariable String name) throws AuthorNotFoundException {
        return authorService.findByName(name);
    }

    @GetMapping
    public List<AuthorDTO> findAll() {
        return authorService.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws AuthorNotFoundException {
        authorService.delete(id);
    }
}
