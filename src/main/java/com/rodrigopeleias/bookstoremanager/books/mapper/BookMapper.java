package com.rodrigopeleias.bookstoremanager.books.mapper;

import com.rodrigopeleias.bookstoremanager.books.dto.BookDTO;
import com.rodrigopeleias.bookstoremanager.books.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book toModel(BookDTO bookDTO);

    BookDTO toDTO(Book bookDTO);
}
