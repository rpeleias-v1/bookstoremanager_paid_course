package com.rodrigopeleias.bookstoremanager.config.jwt.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtResponse {

    private final String jwtToken;
}
