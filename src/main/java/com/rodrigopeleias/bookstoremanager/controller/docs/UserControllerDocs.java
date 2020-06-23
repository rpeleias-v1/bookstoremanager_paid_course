package com.rodrigopeleias.bookstoremanager.controller.docs;

import com.rodrigopeleias.bookstoremanager.dto.MessageDTO;
import com.rodrigopeleias.bookstoremanager.dto.UserDTO;
import com.rodrigopeleias.bookstoremanager.exception.UserAlreadyExistsException;
import com.rodrigopeleias.bookstoremanager.exception.UserNotExistsException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@Api("System users management")
public interface UserControllerDocs {

    @ApiOperation(value = "User creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success user creation"),
            @ApiResponse(code = 400, message = "Missing required fileds, wrong field range value or user already registered on system")
    })
    MessageDTO create(UserDTO userToSaveDTO) throws UserAlreadyExistsException;

    @ApiOperation(value = "User update operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success user update"),
            @ApiResponse(code = 404, message = "User with informed ID not found in the system")
    })
    MessageDTO update(@PathVariable Long id, @RequestBody @Valid UserDTO userToUpdateDTO) throws UserAlreadyExistsException, UserNotExistsException;

    @ApiOperation(value = "Delete update operation")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success user deletion"),
            @ApiResponse(code = 404, message = "User with informed ID not found in the system")
    })
    void delete(Long id) throws UserNotExistsException;
}
