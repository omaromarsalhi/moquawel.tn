package com.moquawel.authentication.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegisterRequest(
        @NotEmpty(message = "First name must not be empty")
        @NotNull(message = "First name must not be empty")
        String firstName,
        @NotEmpty(message = "Last name must not be empty")
        @NotNull(message = "Last name must not be empty")
        String lastName,
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
