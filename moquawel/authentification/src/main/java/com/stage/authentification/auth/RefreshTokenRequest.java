package com.stage.authentification.auth;

public record RefreshTokenRequest(
        String refreshToken,
        String username
) {
}
