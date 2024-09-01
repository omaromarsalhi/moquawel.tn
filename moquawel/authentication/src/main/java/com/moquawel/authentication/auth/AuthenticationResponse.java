package com.moquawel.authentication.auth;

public record AuthenticationResponse(
        String token,
        String refreshToken,
        String firstname,
        String lastname,
        String email,
        String role
) {
}
