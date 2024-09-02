package com.moquawel.authentication.service;


import com.moquawel.authentication.client.UserClient;
import com.moquawel.authentication.customuser.CustomUserDetails;
import com.moquawel.authentication.dto.UserDto;
import com.moquawel.authentication.exception.UserNameAlreadyExists;
import com.moquawel.authentication.request.RegisterRequest;
import com.moquawel.authentication.exception.TokenExpiredException;
import com.moquawel.authentication.exception.UserNotFoundException;
import com.moquawel.authentication.jwt.JwtService;
import com.moquawel.authentication.request.AuthenticationRequest;
import com.moquawel.authentication.request.RefreshTokenRequest;
import com.moquawel.authentication.response.AuthenticationResponse;
import com.moquawel.authentication.response.RefreshResponse;
import com.moquawel.authentication.token.TokenBlackList;
import com.moquawel.authentication.token.TokenBlackListRepository;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final TokenBlackListRepository tokenBlackListRepository;
    private final TokenBlackListService tokenBlackListService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserClient userClient;


    public RefreshResponse register(RegisterRequest request) {

        var user = userClient
                .save(request)
                .orElseThrow(() -> new UserNameAlreadyExists("Account already exists"));
        var tokes = generateTokens(new CustomUserDetails(user));
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

        var user = userClient
                .getUser(request.email())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        tokenBlackListService.removeUserTokens(user.userId());
        var tokes = generateTokens(new CustomUserDetails(user));
        return new AuthenticationResponse(
                tokes.get(0),
                tokes.get(1),
                user.firstName(),
                user.lastName(),
                user.email(),
                user.roles()
        );
    }

    public RefreshResponse refresh(RefreshTokenRequest request) {
        var user = userClient
                .getUser(request.username())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        tokenBlackListService.removeExpiredTokenByUser(
                user.userId(),
                jwtService.extractClaim(request.refreshToken(), Claims::getId)
        );
        return generateMainToken(new CustomUserDetails(user), request.refreshToken());
    }

    private ArrayList<String> generateTokens(CustomUserDetails user) {
        var jwtToken = jwtService.generateToken(user);

        var jwtRefreshToken = jwtService.generateRefreshToken(user.getUsername());
        var refreshTokenBlackList = TokenBlackList.builder()
                .jti(jwtService.extractClaim(jwtRefreshToken, Claims::getId))
                .userId(user.getUserId())
                .created_at(LocalDateTime.now())
                .expires_at(null)
                .build();
        tokenBlackListRepository.save(refreshTokenBlackList);

        return new ArrayList<>(List.of(jwtToken, jwtRefreshToken));
    }

    private RefreshResponse generateMainToken(CustomUserDetails user, String refreshToken) {
        if (jwtService.isTokenValid(refreshToken, user, user.getUserId())) {
            var jwtToken = jwtService.generateToken(user);
            return new RefreshResponse(jwtToken, refreshToken);
        } else
            throw new TokenExpiredException("JWT Token has expired");
    }

}
