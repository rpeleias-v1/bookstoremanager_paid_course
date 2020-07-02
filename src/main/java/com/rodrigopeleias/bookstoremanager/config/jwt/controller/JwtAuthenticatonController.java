package com.rodrigopeleias.bookstoremanager.config.jwt.controller;

import com.rodrigopeleias.bookstoremanager.config.jwt.dto.JwtRequest;
import com.rodrigopeleias.bookstoremanager.config.jwt.dto.JwtResponse;
import com.rodrigopeleias.bookstoremanager.config.jwt.service.JwtTokenManager;
import com.rodrigopeleias.bookstoremanager.config.jwt.service.JwtUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@CrossOrigin
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class JwtAuthenticatonController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenManager jwtTokenManager;

    private final JwtUserDetailsService userDetailsService;

    @PostMapping(value = "/authenticate")
    public JwtResponse createAuthenticationToken(@RequestBody @Valid JwtRequest jwtRequest) {
        authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtTokenManager.generateToken(userDetails);

        return JwtResponse.builder().jwtToken(token).build();
    }

    private void authenticate(String username, String password) {
    }
}
