package com.moquawel.authentication.dto;

import com.moquawel.authentication.role.Role;
import lombok.Builder;

import java.util.ArrayList;

@Builder
public record UserDto(
        String userId,
        String firstName,
        String lastName,
        String email,
        String password,
        ArrayList<Role> roles
) {
}