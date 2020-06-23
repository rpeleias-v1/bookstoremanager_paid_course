package com.rodrigopeleias.bookstoremanager.controller.docs;

import com.rodrigopeleias.bookstoremanager.dto.AuthorDTO;
import com.rodrigopeleias.bookstoremanager.exception.AuthorAlreadyExistsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface AuthorControllerDocs {

    AuthorDTO create(AuthorDTO authorDTO) throws AuthorAlreadyExistsException;
}
