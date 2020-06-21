package com.rodrigopeleias.bookstoremanager.controller;

import com.rodrigopeleias.bookstoremanager.builder.UserDTOBuilder;
import com.rodrigopeleias.bookstoremanager.dto.MessageDTO;
import com.rodrigopeleias.bookstoremanager.dto.UserDTO;
import com.rodrigopeleias.bookstoremanager.service.UserService;
import org.hamcrest.core.Is;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    public static final String USER_API_URL_PATH = "/api/v1/users";
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDTOBuilder userDTOBuilder;

    @BeforeEach
    void setUp() {
        userDTOBuilder = UserDTOBuilder.builder().build();
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenOkStatusIsInformed() throws Exception {
        UserDTO userDTO = userDTOBuilder.buildUserDTO();
        String expectedCreationMessage = "Username rodrigo with ID 1 successfully created";
        MessageDTO expectedCreationMessageDTO = MessageDTO.builder().message(expectedCreationMessage).build();

        when(userService.create(userDTO)).thenReturn(expectedCreationMessageDTO);

        mockMvc.perform(post(USER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", Is.is(expectedCreationMessage)));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenBadRequestStatusIsInformed() throws Exception {
        UserDTO userDTO = userDTOBuilder.buildUserDTO();
        userDTO.setUsername(null);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDTO)))
                .andExpect(status().isBadRequest());
    }
}
