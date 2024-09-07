package com.moquawel.authentication.request;

public record RefreshTokenRequest(
        String refreshToken,
        String username
) {
}
