package com.habittracke.controller;

import com.habittracke.dto.request.UserCreate;
import com.habittracke.dto.response.AccountResponse;
import com.habittracke.service.impl.IRefreshTokenService;
import com.habittracke.service.impl.IUserService;
import com.habittracke.utils.ApiResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {
    public final IUserService userService;
    public final IRefreshTokenService refreshTokenService;
    @PostMapping("/register")
    public AccountResponse register(@RequestBody @Valid UserCreate userCreate, HttpServletRequest request){
        HttpServletRequest session = request;
        return userService.register(userCreate);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> refreshToken(@CookieValue("refreshToken") String refreshToken) {

        return refreshTokenService.refreshToken(refreshToken);

    }
}
