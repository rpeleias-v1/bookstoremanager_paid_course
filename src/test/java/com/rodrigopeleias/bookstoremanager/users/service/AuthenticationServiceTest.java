package com.rodrigopeleias.bookstoremanager.users.service;

import com.rodrigopeleias.bookstoremanager.users.builder.JwtRequestBuilder;
import com.rodrigopeleias.bookstoremanager.users.builder.UserDTOBuilder;
import com.rodrigopeleias.bookstoremanager.users.dto.JwtRequest;
import com.rodrigopeleias.bookstoremanager.users.dto.JwtResponse;
import com.rodrigopeleias.bookstoremanager.users.dto.UserDTO;
import com.rodrigopeleias.bookstoremanager.users.entity.User;
import com.rodrigopeleias.bookstoremanager.users.mapper.UserMapper;
import com.rodrigopeleias.bookstoremanager.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    private static final String ROLE_PREFIX = "ROLE_";

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenManager jwtTokenManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    private JwtRequestBuilder jwtRequestBuilder;

    private UserDTOBuilder userDTOBuilder;

    @BeforeEach
    void setUp() {
        jwtRequestBuilder = JwtRequestBuilder.builder().build();
        userDTOBuilder = UserDTOBuilder.builder().build();
    }

    @Test
    void whenUsernameAndPasswordIsInformedThenTokenShouldBeGenerated() {
        JwtRequest jwtRequest = jwtRequestBuilder.buildJwtRequest();
        UserDTO expectedFoundUserDTO = userDTOBuilder.buildUserDTO();
        User expectedFoundUser = userMapper.toModel(expectedFoundUserDTO);
        String expectedGeneratedToken = "fakeToken";

        when(userRepository.findByUsername(jwtRequest.getUsername())).thenReturn(Optional.of(expectedFoundUser));
        when(jwtTokenManager.generateToken(any(UserDetails.class))).thenReturn(expectedGeneratedToken);

        JwtResponse generatedTokenResponse = authenticationService.createAuthenticationToken(jwtRequest);

        assertThat(generatedTokenResponse.getJwtToken(), is(equalTo(expectedGeneratedToken)));
    }

    @Test
    void whenUsernameIsInformedThenUserShouldBeReturned() {
        UserDTO expectedFoundUserDTO = userDTOBuilder.buildUserDTO();
        User expectedFoundUser = userMapper.toModel(expectedFoundUserDTO);
        SimpleGrantedAuthority expectedUserRole = new SimpleGrantedAuthority(ROLE_PREFIX + expectedFoundUserDTO.getRole().getDescription());

        when(userRepository.findByUsername(expectedFoundUser.getUsername())).thenReturn(Optional.of(expectedFoundUser));

        UserDetails userDetails = authenticationService.loadUserByUsername(expectedFoundUser.getUsername());

        assertThat(userDetails.getUsername(), is(equalTo(expectedFoundUser.getUsername())));
        assertThat(userDetails.getPassword(), is(equalTo(expectedFoundUser.getPassword())));

        assertTrue(userDetails.getAuthorities().contains(expectedUserRole));

    }

    @Test
    void whenInvalidUserIsInformedThenAnExceptionShouldBeThrown() {
        UserDTO expectedFoundUserDTO = userDTOBuilder.buildUserDTO();

        when(userRepository.findByUsername(expectedFoundUserDTO.getUsername())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authenticationService.loadUserByUsername(expectedFoundUserDTO.getUsername()));
    }
}
