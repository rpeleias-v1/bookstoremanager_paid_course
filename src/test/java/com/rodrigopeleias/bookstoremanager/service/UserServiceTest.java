package com.rodrigopeleias.bookstoremanager.service;

import com.rodrigopeleias.bookstoremanager.builder.UserDTOBuilder;
import com.rodrigopeleias.bookstoremanager.dto.MessageDTO;
import com.rodrigopeleias.bookstoremanager.dto.UserDTO;
import com.rodrigopeleias.bookstoremanager.entity.User;
import com.rodrigopeleias.bookstoremanager.exception.UserAlreadyExistsException;
import com.rodrigopeleias.bookstoremanager.mapper.UserMapper;
import com.rodrigopeleias.bookstoremanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserDTOBuilder userDTOBuilder;

    @BeforeEach
    void setUp() {
        userDTOBuilder = UserDTOBuilder.builder().build();
    }

    @Test
    void whenNewUserIsInformedThenItShouldBeCreated() throws UserAlreadyExistsException {
        UserDTO expectedCreatedUserDTO = userDTOBuilder.buildUserDTO();
        User expectedCreatedUser = userMapper.toModel(expectedCreatedUserDTO);
        String expectedCreationMessage = "Username rodrigo with ID 1 successfully created";

        when(userRepository.findByEmailOrUsername(expectedCreatedUserDTO.getEmail(), expectedCreatedUserDTO.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(expectedCreatedUser)).thenReturn(expectedCreatedUser);

        MessageDTO creationMessage = userService.create(expectedCreatedUserDTO);

        assertThat(expectedCreationMessage, is(equalTo(creationMessage.getMessage())));
    }

    @Test
    void whenExistingUserIsInformedThenThrowAnException() {
        UserDTO expectedCreatedUserDTO = userDTOBuilder.buildUserDTO();
        User expectedCreatedUser = userMapper.toModel(expectedCreatedUserDTO);

        when(userRepository.findByEmailOrUsername(expectedCreatedUserDTO.getEmail(), expectedCreatedUserDTO.getUsername())).thenReturn(Optional.of(expectedCreatedUser));

        assertThrows(UserAlreadyExistsException.class, () -> userService.create(expectedCreatedUserDTO));
    }
}
