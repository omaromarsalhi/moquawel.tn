package com.stage.authentification.user;


import lombok.Builder;

@Builder
public record UserDto(
        int idUser,
        String identifier,
        String firstName,
        String lastName,
        String email,
        int idGrade,
        String role
        ) {
}
