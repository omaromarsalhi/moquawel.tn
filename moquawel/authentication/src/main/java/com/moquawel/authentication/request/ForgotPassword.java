package com.moquawel.authentication.request;

public record ForgotPassword(
        String token,
        String newPassword
) {
}
