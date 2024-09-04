package com.moquawel.users.service;


import com.moquawel.users.dto.UserDto;
import com.moquawel.users.exception.UserNameAlreadyExists;
import com.moquawel.users.exception.UserNotFoundException;
import com.moquawel.users.request.RegisterRequest;
import com.moquawel.users.role.Role;
import com.moquawel.users.user.User;
import com.moquawel.users.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserDto getUser(String username) {
        var user = userRepository
                .findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("user not found"));
        return buildUserDto(user);
    }


    public UserDto save(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email()))
            throw new UserNameAlreadyExists("Account already exists");

        var user2save = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles(new ArrayList<>(List.of(Role.SUBSCRIBER, Role.EDITOR)))
                .build();

        userRepository.save(user2save);
        return buildUserDto(user2save);
    }


    public UserDto buildUserDto(User user) {
        return UserDto
                .builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .roles(user.getRoles())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .build();
    }
}
