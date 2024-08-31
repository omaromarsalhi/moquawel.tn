package com.stage.authentification.auth;

public record AuthenticationResponse(
        String token,
        String refreshToken,
        String identifier,
        String firstname,
        String lastname,
        String email,
        String role,
        int idUser
) {
}
