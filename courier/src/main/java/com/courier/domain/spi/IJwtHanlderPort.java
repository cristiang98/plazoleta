package com.courier.domain.spi;

import io.jsonwebtoken.Claims;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

public interface IJwtHanlderPort {

    String extractUsername(String token);
    boolean isTokenValid(String token);
    boolean isTokenExpired(String token);
    Date extractExpiration(String token);
    Claims extractAllClaims(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    Key getSignInKey();
    int extractDni(String token);

}
