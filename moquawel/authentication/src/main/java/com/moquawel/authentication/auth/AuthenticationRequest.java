package com.moquawel.authentication.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AuthenticationRequest(
        @NotEmpty(message = "Email must not be empty")
        @NotNull(message = "Email must not be empty")
        @Pattern(
                regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
                message = "Email must be a valid email address"
        )
        String email,

        @NotEmpty(message = "Password must not be empty")
        @NotNull(message = "Password must not be empty")
        String password
) {
}
