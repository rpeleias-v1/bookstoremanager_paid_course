package com.rodrigopeleias.bookstoremanager.users.controller;

import com.rodrigopeleias.bookstoremanager.users.builder.JwtRequestBuilder;
import com.rodrigopeleias.bookstoremanager.users.builder.UserDTOBuilder;
import com.rodrigopeleias.bookstoremanager.users.dto.JwtRequest;
import com.rodrigopeleias.bookstoremanager.users.dto.JwtResponse;
import com.rodrigopeleias.bookstoremanager.users.dto.MessageDTO;
import com.rodrigopeleias.bookstoremanager.users.dto.UserDTO;
import com.rodrigopeleias.bookstoremanager.users.service.AuthenticationService;
import com.rodrigopeleias.bookstoremanager.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static com.rodrigopeleias.bookstoremanager.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private static final String USER_API_URL_PATH = "/api/v1/users";
    private static final long INVALID_USER_ID = 2L;

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private UserController userController;

    private UserDTOBuilder userDTOBuilder;

    private JwtRequestBuilder jwtRequestBuilder;

    @BeforeEach
    void setUp() {
        jwtRequestBuilder = JwtRequestBuilder.builder().build();
        userDTOBuilder = UserDTOBuilder.builder().build();
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenCreatedShouldBeInformed() throws Exception {
        UserDTO userDTO = userDTOBuilder.buildUserDTO();
        String expectedCreationMessage = "Username rodrigo with ID 1 successfully created";
        MessageDTO expectedCreationMessageDTO = MessageDTO.builder().message(expectedCreationMessage).build();

        when(userService.create(userDTO)).thenReturn(expectedCreationMessageDTO);

        mockMvc.perform(post(USER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(expectedCreationMessage)));
    }

    @Test
    void whenPOSTIsCalledTWithoutRequiredFieldThenBadRequestShouldBeInformed() throws Exception {
        UserDTO userDTO = userDTOBuilder.buildUserDTO();
        userDTO.setUsername(null);

        mockMvc.perform(post(USER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPOSTIsCalledToAuthenticateUserThenOkShouldBeInformed() throws Exception {
        JwtRequest jwtRequest = jwtRequestBuilder.buildJwtRequest();
        JwtResponse expectedJwtToken = JwtResponse.builder().jwtToken("testToken").build();

        when(authenticationService.createAuthenticationToken(jwtRequest)).thenReturn(expectedJwtToken);

        mockMvc.perform(post(USER_API_URL_PATH + "/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(jwtRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwtToken", is(expectedJwtToken.getJwtToken())));
    }

    @Test
    void whenPOSTIsCalledToAuthenticateUserWithoutPasswordThenBadRequestShouldBeInformed() throws Exception {
        JwtRequest jwtRequest = jwtRequestBuilder.buildJwtRequest();
        jwtRequest.setPassword(null);

        mockMvc.perform(post(USER_API_URL_PATH + "/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(jwtRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPUTIsCalledThenOkStatusShouldBeInformed() throws Exception {
        UserDTO userDTO = userDTOBuilder.buildUserDTO();
        userDTO.setName("Rodrigo updated");
        String expectedCreationMessage = "Username Rodrigo updated with ID 1 successfully updated";
        MessageDTO expectedUpdatedMessageDTO = MessageDTO.builder().message(expectedCreationMessage).build();

        when(userService.update(userDTO.getId(), userDTO)).thenReturn(expectedUpdatedMessageDTO);

        mockMvc.perform(put(USER_API_URL_PATH + "/" + userDTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedCreationMessage)));
    }

    @Test
    void whenDELETEIsCalledThenNoContentShouldBeInformed() throws Exception {
        UserDTO userDTO = userDTOBuilder.buildUserDTO();

        doNothing().when(userService).delete(userDTO.getId());

        mockMvc.perform(delete(USER_API_URL_PATH + "/" + userDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
