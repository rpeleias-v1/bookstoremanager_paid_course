package com.rodrigopeleias.bookstoremanager.authors.service;

import com.rodrigopeleias.bookstoremanager.authors.dto.AuthorDTO;
import com.rodrigopeleias.bookstoremanager.authors.entity.Author;
import com.rodrigopeleias.bookstoremanager.authors.exception.AuthorAlreadyExistsException;
import com.rodrigopeleias.bookstoremanager.authors.exception.AuthorNotFoundException;
import com.rodrigopeleias.bookstoremanager.authors.mapper.AuthorMapper;
import com.rodrigopeleias.bookstoremanager.authors.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorService {

    private final static AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    private final AuthorRepository authorRepository;

    public AuthorDTO create(AuthorDTO authorDTO) {
        verifyIfExists(authorDTO.getName());
        Author authorToCreate = authorMapper.toModel(authorDTO);
        Author createdAuthor = authorRepository.save(authorToCreate);
        return authorMapper.toDTO(createdAuthor);
    }

    public AuthorDTO findByName(String name) {
        Author foundAuthor = authorRepository.findByName(name)
                .orElseThrow(() -> new AuthorNotFoundException(name));
        return authorMapper.toDTO(foundAuthor);
    }

    public List<AuthorDTO> findAll() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Author verifyAndGetIfExists(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }

    public Author createOrUpdateIfExists(AuthorDTO authorDTO) {
        Optional<Author> optExistingAuthor = authorRepository.findByName(authorDTO.getName());
        if (optExistingAuthor.isPresent()) {
            return update(authorDTO, optExistingAuthor.get());
        }
        AuthorDTO createdAuthorDTO = create(authorDTO);
        return authorMapper.toModel(createdAuthorDTO);
    }

    public void delete(Long id) {
        verifyAndGetIfExists(id);
        authorRepository.deleteById(id);
    }

    private Author update(AuthorDTO authorDTO, Author existingAuthor) {
        existingAuthor.setName(authorDTO.getName());
        existingAuthor.setAge(authorDTO.getAge());
        return authorRepository.save(existingAuthor);
    }

    private void verifyIfExists(String authorName) {
        authorRepository.findByName(authorName)
                .ifPresent(author -> {
                    throw new AuthorAlreadyExistsException(authorName);
                });
    }
}
