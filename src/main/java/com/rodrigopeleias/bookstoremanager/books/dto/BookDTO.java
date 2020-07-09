package com.rodrigopeleias.bookstoremanager.books.dto;

import com.rodrigopeleias.bookstoremanager.authors.dto.AuthorDTO;
import com.rodrigopeleias.bookstoremanager.publishers.dto.PublisherDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.ISBN;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private Long id;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String name;

    @ISBN
    private String isbn;

    @Valid
    @NotNull
    private PublisherDTO publisher;

    @Valid
    @NotNull
    private AuthorDTO author;

    @NotNull
    @Max(3000)
    private Integer pages;

    @NotNull
    @Max(100)
    private Integer chapters;
}
