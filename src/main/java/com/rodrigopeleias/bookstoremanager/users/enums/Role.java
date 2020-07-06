package com.rodrigopeleias.bookstoremanager.users.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    ADMIN("ADMIN"),
    USER("USER");

    private final String description;
}
