package com.rodrigopeleias.bookstoremanager.books.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.ISBN;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDTO {

    private Long id;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String name;

    @NotNull
    @ISBN
    private String isbn;

    @NotNull
    private Long publisherId;

    @NotNull
    private Long authorId;

    @NotNull
    @Max(3000)
    private Integer pages;

    @NotNull
    @Max(100)
    private Integer chapters;
}
