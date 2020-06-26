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

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    public AuthorDTO create(AuthorDTO authorDTO) throws AuthorAlreadyExistsException {
        verifyIfExists(authorDTO.getName());
        Author authorToCreate = authorMapper.toModel(authorDTO);
        Author createdAuthor = authorRepository.save(authorToCreate);
        return authorMapper.toDTO(createdAuthor);
    }

    public AuthorDTO findByName(String name) throws AuthorNotFoundException {
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

    public void delete(Long id) throws AuthorNotFoundException {
        verifyIfExists(id);
        authorRepository.deleteById(id);
    }

    private void verifyIfExists(String authorName) throws AuthorAlreadyExistsException {
        Optional<Author> duplicatedAuthor = authorRepository.findByName(authorName);
        if (duplicatedAuthor.isPresent()) {
            throw new AuthorAlreadyExistsException(authorName);
        }
    }

    private void verifyIfExists(Long id) throws AuthorNotFoundException {
        authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }
}
