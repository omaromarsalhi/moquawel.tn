package com.moquawel.authentication.auth;

public record RefreshTokenRequest(
        String refreshToken,
        String username
) {
}
