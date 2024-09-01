package com.moquawel.gateway.jwt;


import com.moquawel.gateway.exeption.TokenMissingException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {


    private JwtService jwtService;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final List<String> excludedPaths = List.of("/api/v1/auth/**", "/api/v1/users/**");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        try {
            String path = exchange.getRequest().getURI().getPath();
            System.out.println(path);
//            if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
//                return handleCorsPreflight(exchange);
//            }

            if (isExcluded(path)) {
                return chain.filter(exchange);
            }


            String token = extractToken(exchange.getRequest().getHeaders().getFirst("Authorization"));

            if (token == null || jwtService.isTokenValid(token)) {
                throw new TokenMissingException("expired");
            }

            return chain.filter(exchange);
        } catch (TokenMissingException | ExpiredJwtException e) {
            String errorMessage = "{\"token\": \"JWT Token has expired Donkey\"}";
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            exchange.getResponse().getHeaders().set("Access-Control-Allow-Origin", "http://localhost:3000");
            exchange.getResponse().getHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            exchange.getResponse().getHeaders().set("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token");
            DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
            DataBuffer buffer = bufferFactory.wrap(errorMessage.getBytes(StandardCharsets.UTF_8));
            return exchange.getResponse().writeWith(Flux.just(buffer));
        }

    }

    private boolean isExcluded(String path) {
        return excludedPaths.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    private String extractToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else
            throw new TokenMissingException("missing token");
    }


//    private Mono<Void> handleCorsPreflight(ServerWebExchange exchange) {
//        exchange.getResponse().getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:3000");
//        exchange.getResponse().getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE, OPTIONS");
//        exchange.getResponse().getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Authorization, Content-Type, Accept");
//        exchange.getResponse().getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
//        exchange.getResponse().setStatusCode(HttpStatus.OK);
//        return exchange.getResponse().setComplete();
//    }
}
