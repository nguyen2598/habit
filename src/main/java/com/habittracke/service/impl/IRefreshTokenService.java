package com.habittracke.service.impl;

import com.habittracke.dto.response.UserDTO;
import com.habittracke.entity.sql.RefreshToken;
import com.habittracke.entity.sql.User;
import com.habittracke.repository.RefreshTokenRepository;
import com.habittracke.repository.UserRepository;
import com.habittracke.service.RefreshTokenService;
import com.habittracke.utils.ApiResponseUtil;
import com.habittracke.utils.JwtToken;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IRefreshTokenService implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    public final UserRepository userRepository;
    public final IUserService userService;
    private final JwtToken jwtToken;
    @Override
    @Transactional
    public RefreshToken createRefreshToken(String userId) {
        RefreshToken token = new RefreshToken();
        token.setUserId(userId);
        token.setExpiryDate(Instant.now().plusSeconds(600000));
        token.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(token);
    }

    @Override
    public Optional<RefreshToken> verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            return Optional.empty();
        }
        return Optional.of(token);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public ResponseEntity<Object> refreshToken(String tokenData) {
        return refreshTokenRepository.findByToken(tokenData)
        .flatMap(this::verifyExpiration)
                .map(token -> {
                    String userId = token.getUserId();
                    UserDTO user = userService.getUserById(userId);
                    String accessToken = jwtToken.generateToken(user);
                    return ApiResponseUtil.ok(accessToken);
                })
                .orElseGet(() -> ApiResponseUtil.unauthorized("invalid.refresh.token"));
    }
}
