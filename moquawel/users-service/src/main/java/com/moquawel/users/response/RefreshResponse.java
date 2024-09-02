package com.moquawel.users.response;

public record RefreshResponse(
        String token,
        String refreshToken
) {
}
