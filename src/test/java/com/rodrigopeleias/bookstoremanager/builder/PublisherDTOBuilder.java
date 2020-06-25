package com.rodrigopeleias.bookstoremanager.builder;

import com.rodrigopeleias.bookstoremanager.dto.PublisherDTO;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class PublisherDTOBuilder {

    @Builder.Default
    private final Long id = 1L;

    @Builder.Default
    private final String name = "Rodrigo Peleias";

    @Builder.Default
    private final String code = "PUB2020";

    @Builder.Default
    private final LocalDate foundationDate = LocalDate.of(2020, 6, 1);

    public PublisherDTO buildPublisherDTO() {
        return new PublisherDTO(id,
                name,
                code,
                foundationDate);
    }
}
