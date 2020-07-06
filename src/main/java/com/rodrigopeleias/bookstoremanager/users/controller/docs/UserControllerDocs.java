package com.rodrigopeleias.bookstoremanager.users.controller.docs;


import com.rodrigopeleias.bookstoremanager.users.dto.JwtRequest;
import com.rodrigopeleias.bookstoremanager.users.dto.JwtResponse;
import com.rodrigopeleias.bookstoremanager.users.dto.MessageDTO;
import com.rodrigopeleias.bookstoremanager.users.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api("System users management")
public interface UserControllerDocs {

    @ApiOperation(value = "User creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success user creation"),
            @ApiResponse(code = 400, message = "Missing required fileds, wrong field range value or user already registered on system")
    })
    MessageDTO create(UserDTO userToSaveDTO);

    @ApiOperation(value = "User authentication operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success user authenticated"),
            @ApiResponse(code = 440, message = "User not found")
    })
    JwtResponse createAuthenticationToken(JwtRequest jwtRequest);

    @ApiOperation(value = "User update operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success user update"),
            @ApiResponse(code = 404, message = "User with informed ID not found in the system")
    })
    MessageDTO update(@PathVariable Long id, @RequestBody @Valid UserDTO userToUpdateDTO);

    @ApiOperation(value = "Delete update operation")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success user deletion"),
            @ApiResponse(code = 404, message = "User with informed ID not found in the system")
    })
    void delete(Long id);
}
