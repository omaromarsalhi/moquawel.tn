package com.moquawel.authentication.auth;

import com.moquawel.authentication.role.Role;

import java.util.ArrayList;

public record AuthenticationResponse(
        String token,
        String refreshToken,
        String firstname,
        String lastname,
        String email,
        ArrayList<Role> roles
) {
}
