package com.moquawel.authentication.auth;

public record RefreshResponse(
        String token,
        String refreshToken
) {
}
