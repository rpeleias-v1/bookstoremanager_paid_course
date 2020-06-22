package com.rodrigopeleias.bookstoremanager.controller;

import com.rodrigopeleias.bookstoremanager.controller.docs.UserControllerDoc;
import com.rodrigopeleias.bookstoremanager.dto.MessageDTO;
import com.rodrigopeleias.bookstoremanager.dto.UserDTO;
import com.rodrigopeleias.bookstoremanager.exception.UserAlreadyExistsException;
import com.rodrigopeleias.bookstoremanager.exception.UserNotExistsException;
import com.rodrigopeleias.bookstoremanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController implements UserControllerDoc {

    @Autowired
    private UserService userService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDTO create(@RequestBody @Valid UserDTO userToSaveDTO) throws UserAlreadyExistsException {
        return userService.create(userToSaveDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MessageDTO update(@PathVariable Long id, @RequestBody @Valid UserDTO userToUpdateDTO) throws UserAlreadyExistsException, UserNotExistsException {
        return userService.update(id, userToUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws UserNotExistsException {
        userService.delete(id);
    }
}
