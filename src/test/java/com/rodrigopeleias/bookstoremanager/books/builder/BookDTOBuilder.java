package com.rodrigopeleias.bookstoremanager.books.builder;

import com.rodrigopeleias.bookstoremanager.authors.builder.AuthorDTOBuilder;
import com.rodrigopeleias.bookstoremanager.authors.dto.AuthorDTO;
import com.rodrigopeleias.bookstoremanager.books.dto.BookRequestDTO;
import com.rodrigopeleias.bookstoremanager.publishers.builder.PublisherDTOBuilder;
import com.rodrigopeleias.bookstoremanager.publishers.dto.PublisherDTO;
import com.rodrigopeleias.bookstoremanager.users.builder.UserDTOBuilder;
import com.rodrigopeleias.bookstoremanager.users.dto.UserDTO;
import lombok.Builder;

@Builder
public class BookDTOBuilder {

    @Builder.Default
    private final Long id = 1L;

    @Builder.Default
    private final String name = "Spring Boot Pro";

    @Builder.Default
    private final String isbn = "0-596-52068-9";

    @Builder.Default
    private final Long publisherId = 2L;

    @Builder.Default
    private final Long authorId = 3L;

    @Builder.Default
    private final Integer pages = 200;

    @Builder.Default
    private final Integer chapters = 10;

    private final UserDTO userDTO = UserDTOBuilder.builder().build().buildUserDTO();

    public BookRequestDTO buildBookDTO() {
        return new BookRequestDTO(id,
                name,
                isbn,
                publisherId,
                authorId,
                pages,
                chapters);
    }
}
