package com.habittracke.utils;


import com.habittracke.dto.response.UserDTO;
import com.habittracke.entity.sql.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtToken {
    @Value("${jwt.signerKey}")
    private String signerKey;

    @Value("${jwt.valid-duration}")
    private long expirationTime;

    public String generateToken(UserDTO user) {
        Date now = new Date();
        Date expiry  = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(user.getId())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(Keys.hmacShaKeyFor(signerKey.getBytes()), SignatureAlgorithm.HS256)
                .claim("scope", user)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(signerKey.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true;
        }
        catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        } catch (Exception ex){
            log.error("Invalid JWT token - {}", ex.getMessage());
        }
        return false;
    }
}
