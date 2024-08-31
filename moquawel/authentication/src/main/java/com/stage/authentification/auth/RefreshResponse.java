package com.stage.authentification.auth;

public record RefreshResponse(
        String token,
        String refreshToken
) {
}
