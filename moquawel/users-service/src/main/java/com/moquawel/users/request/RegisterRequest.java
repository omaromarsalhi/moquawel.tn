package com.moquawel.users.request;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
