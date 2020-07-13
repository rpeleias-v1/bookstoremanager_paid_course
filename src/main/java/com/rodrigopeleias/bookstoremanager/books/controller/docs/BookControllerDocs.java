package com.rodrigopeleias.bookstoremanager.books.controller.docs;

import com.rodrigopeleias.bookstoremanager.books.dto.BookRequestDTO;
import com.rodrigopeleias.bookstoremanager.books.dto.BookResponseDTO;
import com.rodrigopeleias.bookstoremanager.users.dto.AuthenticatedUser;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

public interface BookControllerDocs {

    @ApiOperation(value = "Book creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success book creation"),
            @ApiResponse(code = 400, message = "Missing required fields, wrong field range value or book already registered on system")
    })
    BookResponseDTO create(AuthenticatedUser authenticatedUser, BookRequestDTO bookRequestDTO);

    @ApiOperation(value = "Book find by id and user operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success book found"),
            @ApiResponse(code = 404, message = "Book not found error")
    })
    BookResponseDTO findByIdAndUser(AuthenticatedUser authenticatedUser, Long id);

    @ApiOperation(value = "List all books by a specific authenticated user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book list found by authenticated user informed")
    })
    List<BookResponseDTO> findAllByUser(AuthenticatedUser authenticatedUser);

    @ApiOperation(value = "Book update operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book by user successfully updated"),
            @ApiResponse(code = 404, message = "Book not found error"),
            @ApiResponse(code = 400, message = "Missing required fields, wrong field range value or book already registered on system")
    })
    BookResponseDTO updateByUser(AuthenticatedUser authenticatedUser, Long bookId, BookRequestDTO bookRequestDTO);

    @ApiOperation(value = "Book delete operation")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Book by user successfully deleted"),
            @ApiResponse(code = 404, message = "Book not found error")
    })
    void deleteByIdAndUser(AuthenticatedUser authenticatedUser, Long bookId);
}
