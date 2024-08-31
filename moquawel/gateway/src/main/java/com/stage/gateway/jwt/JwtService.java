package com.stage.gateway.jwt;


import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import java.util.Date;

@Service
public class JwtService {

    private final String SECRET_KEY = "3A3819FB8D35506C579DB26CC2B33952550D02BE99FBF626D8F9C4E8E934F1A3";


    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

}
