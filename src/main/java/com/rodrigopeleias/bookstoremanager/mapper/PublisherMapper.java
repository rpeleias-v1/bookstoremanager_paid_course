package com.rodrigopeleias.bookstoremanager.mapper;

import com.rodrigopeleias.bookstoremanager.dto.PublisherDTO;
import com.rodrigopeleias.bookstoremanager.entity.Publisher;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PublisherMapper {

    PublisherMapper INSTANCE = Mappers.getMapper(PublisherMapper.class);

    Publisher toModel(PublisherDTO publisherDTO);

    PublisherDTO toDTO(Publisher publisher);
}
