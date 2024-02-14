package com.mynotion.user.controller;

import com.mynotion.common.SuccessResponse;
import com.mynotion.oauth.application.OAuthService;
import com.mynotion.user.model.User;
import com.mynotion.user.model.UserDto;
import com.mynotion.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Value("${jwt.refreshTokenExpirationMs}")
    private long refreshTokenExpirationMs;
    private final UserService userService;
    private final OAuthService oauthService;

    @Autowired
    public UserController(UserService userService, OAuthService oauthService) {
        this.userService = userService;
        this.oauthService = oauthService;
    }

    @PostMapping("/join")
    public ResponseEntity<SuccessResponse<String>> joinUser(@RequestBody UserDto user, HttpServletResponse response) {
        userService.registerUser(user);
        Map<String,String> tokens = oauthService.generateTokens(user.getUsername());

        // 쿠키 생성
        Cookie refreshTokenCookie = new Cookie("refreshToken", tokens.get("refreshToken"));
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge((int) TimeUnit.MILLISECONDS.toSeconds(refreshTokenExpirationMs));
        refreshTokenCookie.setPath("/");
        response.addCookie(refreshTokenCookie);

        return new ResponseEntity<>(new SuccessResponse<>("S-01", tokens.get("accessToken"), "User successfully created"), HttpStatus.CREATED);
    }
}

