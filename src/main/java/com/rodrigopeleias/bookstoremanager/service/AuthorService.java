package com.rodrigopeleias.bookstoremanager.service;

import com.rodrigopeleias.bookstoremanager.dto.AuthorDTO;
import com.rodrigopeleias.bookstoremanager.entity.Author;
import com.rodrigopeleias.bookstoremanager.exception.AuthorAlreadyExistsException;
import com.rodrigopeleias.bookstoremanager.exception.AuthorNotFoundException;
import com.rodrigopeleias.bookstoremanager.mapper.AuthorMapper;
import com.rodrigopeleias.bookstoremanager.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    public AuthorDTO create(AuthorDTO authorDTO) throws AuthorAlreadyExistsException {
        verifyIfExists(authorDTO.getName());
        Author authorToCreate = authorMapper.toModel(authorDTO);
        Author createdAuthor = authorRepository.save(authorToCreate);
        return authorMapper.toDTO(createdAuthor);
    }

    private void verifyIfExists(String authorName) throws AuthorAlreadyExistsException {
        Optional<Author> duplicatedAuthor = authorRepository.findByName(authorName);
        if (duplicatedAuthor.isPresent()) {
            throw new AuthorAlreadyExistsException(authorName);
        }
    }

    public AuthorDTO findByName(String name) throws AuthorNotFoundException {
        Author foundAuthor = authorRepository.findByName(name)
                .orElseThrow(() -> new AuthorNotFoundException(name));
        return authorMapper.toDTO(foundAuthor);
    }
}
