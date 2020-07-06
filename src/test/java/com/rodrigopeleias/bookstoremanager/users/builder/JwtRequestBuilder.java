package com.rodrigopeleias.bookstoremanager.users.builder;

import com.rodrigopeleias.bookstoremanager.users.dto.JwtRequest;
import lombok.Builder;

@Builder
public class JwtRequestBuilder {

    @Builder.Default
    private String username = "rodrigo";

    @Builder.Default
    private String password = "123456";

    public JwtRequest buildJwtRequest() {
        return new JwtRequest(username, password);
    }
}
