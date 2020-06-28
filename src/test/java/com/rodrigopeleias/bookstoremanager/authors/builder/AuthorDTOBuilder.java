package com.rodrigopeleias.bookstoremanager.authors.builder;

import com.rodrigopeleias.bookstoremanager.authors.dto.AuthorDTO;
import lombok.Builder;

@Builder
public class AuthorDTOBuilder {

    @Builder.Default
    private final Long id = 1L;

    @Builder.Default
    private final String name = "Rodrigo Peleias";

    @Builder.Default
    private final int age = 32;
    
    public AuthorDTO buildAuthorDTO() {
        return new AuthorDTO(id, name, age);
    }
}
