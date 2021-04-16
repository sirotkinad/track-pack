package com.trackpack.app.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.OffsetDateTime;
import java.util.Date;

@Component
public class JWTProvider {

    @Value("$(jwt.secret)")
    private String jwtSecret;
    private final int VALID_TIME_AT_HOURS = 720;

    public String generateToken(String email) {
        Date expirationDate = Date.from(OffsetDateTime.now().plusHours(VALID_TIME_AT_HOURS).toInstant());
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException ex ) {
            return false;
        }
    }

}
