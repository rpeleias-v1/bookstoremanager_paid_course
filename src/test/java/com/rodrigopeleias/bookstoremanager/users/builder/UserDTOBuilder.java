package com.rodrigopeleias.bookstoremanager.users.builder;

import com.rodrigopeleias.bookstoremanager.users.dto.UserDTO;
import com.rodrigopeleias.bookstoremanager.users.enums.Gender;
import com.rodrigopeleias.bookstoremanager.users.enums.Role;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class UserDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Rodrigo";

    @Builder.Default
    private int age = 32;

    @Builder.Default
    private Gender gender = Gender.MALE;

    @Builder.Default
    private String email = "rodrigo@course.com";

    @Builder.Default
    private String username = "rodrigo";

    @Builder.Default
    private String password = "123456";

    @Builder.Default
    private LocalDate birthdate = LocalDate.of(1988, 3, 23);

    @Builder.Default
    private Role role = Role.USER;

    public UserDTO buildUserDTO() {
        return new UserDTO(id,
                name,
                age,
                gender,
                email,
                username,
                password,
                birthdate,
                role);
    }


}
