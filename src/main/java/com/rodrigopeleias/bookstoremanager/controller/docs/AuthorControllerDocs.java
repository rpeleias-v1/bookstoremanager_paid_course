package com.rodrigopeleias.bookstoremanager.controller.docs;

import com.rodrigopeleias.bookstoremanager.dto.AuthorDTO;
import com.rodrigopeleias.bookstoremanager.exception.AuthorAlreadyExistsException;
import com.rodrigopeleias.bookstoremanager.exception.AuthorNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Authors management")
public interface AuthorControllerDocs {

    @ApiOperation(value = "Author creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success author creation"),
            @ApiResponse(code = 400, message = "Missing required fileds, wrong field range value or author already registered on system")
    })
    AuthorDTO create(AuthorDTO authorDTO) throws AuthorAlreadyExistsException;

    @ApiOperation(value = "Find author by name operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success author found"),
            @ApiResponse(code = 404, message = "Author not found error code")
    })
    AuthorDTO findByName(String name) throws AuthorNotFoundException;
}
