package com.moquawel.authentication.auth;


import com.moquawel.authentication.exception.TokenExpiredException;
import com.moquawel.authentication.exception.UserNotFoundException;
import com.moquawel.authentication.jwt.JwtService;
import com.moquawel.authentication.token.TokenBlackList;
import com.moquawel.authentication.token.TokenBlackListRepository;
import com.moquawel.authentication.role.ERole;
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

import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenBlackListRepository tokenBlackListRepository;
    private final TokenBlackListService tokenBlackListService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public ArrayList<String> register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(ERole.ADMIN)
                .build();

        userRepository.save(user);
        return generateTokens(user);
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
        tokenBlackListService.expireAllTokensByUser(user.getUserId());
        var tokes = generateTokens(user);
        return new AuthenticationResponse(
                tokes.get(0),
                tokes.get(1),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole().name()
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
//                .user(user)
                .build();
        tokenBlackListRepository.save(tokenBlackList);

        var jwtRefreshToken = jwtService.generateRefreshToken(user.getEmail());
        var refreshTokenBlackList = TokenBlackList.builder()
                .jti(jwtService.extractClaim(jwtRefreshToken, Claims::getId))
//                .user(user)
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
//                    .user(user)
                    .build();
            tokenBlackListRepository.save(tokenBlackList);

            return new RefreshResponse(jwtToken, refreshToken);
        } else
            throw new TokenExpiredException("JWT Token has expired");
    }

}
