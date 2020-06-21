package com.rodrigopeleias.bookstoremanager.service;

import com.rodrigopeleias.bookstoremanager.dto.MessageDTO;
import com.rodrigopeleias.bookstoremanager.dto.UserDTO;
import com.rodrigopeleias.bookstoremanager.entity.User;
import com.rodrigopeleias.bookstoremanager.exception.UserAlreadyExistsException;
import com.rodrigopeleias.bookstoremanager.mapper.UserMapper;
import com.rodrigopeleias.bookstoremanager.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper = UserMapper.INSTANCE;

    public MessageDTO create(UserDTO userToCreateDTO) throws UserAlreadyExistsException {
        verifyIfExists(userToCreateDTO.getEmail(), userToCreateDTO.getUsername());
        User userToCreate = userMapper.toModel(userToCreateDTO);
        User createdUser = userRepository.save(userToCreate);
        return creationMessage(createdUser);
    }

    private MessageDTO creationMessage(User createdUser) {
        return returnMessage("created", createdUser);
    }

    private MessageDTO returnMessage(String action, User user) {
        return MessageDTO.builder()
                .message(String.format("Username %s with ID %s successfully %s", user.getUsername(), user.getId(), action))
                .build();
    }

    private void verifyIfExists(String email, String username) throws UserAlreadyExistsException {
        Optional<User> foundUser = userRepository.findByEmailOrUsername(email, username);
        if (foundUser.isPresent()) {
            throw new UserAlreadyExistsException(email, username);
        }

    }
}
