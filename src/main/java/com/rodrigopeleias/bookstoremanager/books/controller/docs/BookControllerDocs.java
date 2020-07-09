package com.rodrigopeleias.bookstoremanager.books.controller.docs;

import com.rodrigopeleias.bookstoremanager.books.dto.BookDTO;
import com.rodrigopeleias.bookstoremanager.users.dto.AuthenticatedUser;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public interface BookControllerDocs {

    @ApiOperation(value = "Book creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success book creation"),
            @ApiResponse(code = 400, message = "Missing required fields, wrong field range value or book already registered on system")
    })
    BookDTO create(AuthenticatedUser authenticatedUser, BookDTO bookDTO);
}
