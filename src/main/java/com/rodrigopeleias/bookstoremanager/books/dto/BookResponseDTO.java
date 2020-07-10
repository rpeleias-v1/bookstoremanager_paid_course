package com.rodrigopeleias.bookstoremanager.books.dto;

import com.rodrigopeleias.bookstoremanager.authors.dto.AuthorDTO;
import com.rodrigopeleias.bookstoremanager.publishers.dto.PublisherDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO {

    private Long id;

    private String name;

    private String isbn;

    private PublisherDTO publisher;

    private AuthorDTO author;

    private Integer pages;

    private Integer chapters;
}
