package com.moquawel.authentication.auth;


import com.moquawel.authentication.exception.TokenExpiredException;
import com.moquawel.authentication.exception.UserNameAlreadyExists;
import com.moquawel.authentication.exception.UserNotFoundException;
import com.moquawel.authentication.jwt.JwtService;
import com.moquawel.authentication.token.TokenBlackList;
import com.moquawel.authentication.token.TokenBlackListRepository;
import com.moquawel.authentication.role.Role;
import com.moquawel.authentication.token.TokenBlackListService;
import com.moquawel.authentication.user.User;
import com.moquawel.authentication.user.UserRepository;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenBlackListRepository tokenBlackListRepository;
    private final TokenBlackListService tokenBlackListService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public RefreshResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email()))
            throw new UserNameAlreadyExists("account already exists");

        var user2save = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles(new ArrayList<>(List.of(Role.SUBSCRIBER, Role.EDITOR)))
                .build();

        userRepository.save(user2save);
        var tokes = generateTokens(user2save);
        return new RefreshResponse(tokes.get(0), tokes.get(1));

    }


    public AuthenticationResponse login(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
        } catch (BadCredentialsException ex) {
            throw new UserNotFoundException("Invalid email or password");
        }

        var user = userRepository
                .findByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        tokenBlackListService.removeUserTokens(user.getUserId());
        var tokes = generateTokens(user);
        return new AuthenticationResponse(
                tokes.get(0),
                tokes.get(1),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRoles()
        );
    }

    public RefreshResponse refresh(RefreshTokenRequest request) {
        var user = userRepository.findByEmail(request.username()).orElseThrow(() -> new UserNotFoundException("User not found"));
        tokenBlackListService.expireTokenByUser(user.getUserId(), jwtService.extractClaim(request.refreshToken(), Claims::getId));
        return generateMainToken(user, request.refreshToken());
    }

    private ArrayList<String> generateTokens(User user) {
        var list = new ArrayList<String>();
        var jwtToken = jwtService.generateToken(user);
        var tokenBlackList = TokenBlackList.builder()
                .jti(jwtService.extractClaim(jwtToken, Claims::getId))
                .userId(user.getUserId())
                .created_at(LocalDateTime.now())
                .expires_at(null)
                .build();

        tokenBlackListRepository.save(tokenBlackList);

        var jwtRefreshToken = jwtService.generateRefreshToken(user.getEmail());
        var refreshTokenBlackList = TokenBlackList.builder()
                .jti(jwtService.extractClaim(jwtRefreshToken, Claims::getId))
                .userId(user.getUserId())
                .created_at(LocalDateTime.now())
                .expires_at(null)
                .build();
        tokenBlackListRepository.save(refreshTokenBlackList);

        list.add(jwtToken);
        list.add(jwtRefreshToken);
        return list;
    }

    private RefreshResponse generateMainToken(User user, String refreshToken) {
        if (jwtService.isTokenValid(refreshToken, user)) {
            var jwtToken = jwtService.generateToken(user);
            var tokenBlackList = TokenBlackList.builder()
                    .jti(jwtService.extractClaim(jwtToken, Claims::getId))
                    .userId(user.getUserId())
                    .build();
            tokenBlackListRepository.save(tokenBlackList);

            return new RefreshResponse(jwtToken, refreshToken);
        } else
            throw new TokenExpiredException("JWT Token has expired");
    }

}
