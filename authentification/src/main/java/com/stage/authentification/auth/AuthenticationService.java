package com.stage.authentification.auth;


import com.stage.authentification.exception.TokenExpiredException;
import com.stage.authentification.exception.UserNotFoundException;
import com.stage.authentification.jwt.JwtService;
import com.stage.authentification.token.TokenBlackList;
import com.stage.authentification.token.TokenBlackListRepository;
import com.stage.authentification.user.Role;
import com.stage.authentification.user.User;
import com.stage.authentification.user.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenBlackListRepository tokenBlackListRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public ArrayList<String> register(RegisterRequest request) {
        var list = new ArrayList<String>();
        var user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.ADMIN)
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        list.add(jwtToken);
        list.add(jwtToken);
        return list;
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

        var user = userRepository.findByEmail(request.email()).orElseThrow(() -> new UserNotFoundException("User not found"));
        tokenBlackListRepository.expireAllTokensByUser(user.getIdUser());
        var tokes = generateTokens(user);
        return new AuthenticationResponse(
                tokes.get(0),
                tokes.get(1),
                user.getIdentifier(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole().name(),
                user.getIdUser()
        );
    }

    public RefreshResponse refresh(RefreshTokenRequest request) {
        var user = userRepository.findByEmail(request.username()).orElseThrow(() -> new UserNotFoundException("User not found"));
        tokenBlackListRepository.expireTokenByUser(user.getIdUser(),jwtService.extractClaim(request.refreshToken(), Claims::getId));
        return generateMainToken(user, request.refreshToken());
    }

    private ArrayList<String> generateTokens(User user) {
        var list = new ArrayList<String>();
        var jwtToken = jwtService.generateToken(user);
        var tokenBlackList = TokenBlackList.builder()
                .jti(jwtService.extractClaim(jwtToken, Claims::getId))
                .user(user)
                .build();
        tokenBlackListRepository.save(tokenBlackList);

        var jwtRefreshToken = jwtService.generateRefreshToken(user.getEmail());
        var refreshTokenBlackList = TokenBlackList.builder()
                .jti(jwtService.extractClaim(jwtRefreshToken, Claims::getId))
                .user(user)
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
                    .user(user)
                    .build();
            tokenBlackListRepository.save(tokenBlackList);

            return new RefreshResponse(jwtToken, refreshToken);
        } else
            throw new TokenExpiredException("JWT Token has expired");
    }

}
