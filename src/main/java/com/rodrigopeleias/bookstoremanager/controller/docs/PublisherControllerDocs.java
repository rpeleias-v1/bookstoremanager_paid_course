package com.rodrigopeleias.bookstoremanager.controller.docs;

import com.rodrigopeleias.bookstoremanager.dto.PublisherDTO;
import com.rodrigopeleias.bookstoremanager.exception.PublisherAlreadyExistsException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@Api("Publishers management")
public interface PublisherControllerDocs {

    @ApiOperation(value = "Publisher creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success publisher creation"),
            @ApiResponse(code = 400, message = "Missing required fileds, wrong field range value or user already registered on system")
    })
    PublisherDTO create(@RequestBody @Valid PublisherDTO publisherDTO) throws PublisherAlreadyExistsException;
}
