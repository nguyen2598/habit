package com.habittracke.service;

import com.habittracke.entity.sql.RefreshToken;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(String userId);
    Optional<RefreshToken> verifyExpiration(RefreshToken token);
    Optional<RefreshToken> findByToken(String token);
    ResponseEntity<Object> refreshToken(String token);
}
