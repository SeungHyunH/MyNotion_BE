package com.mynotion.oauth.presentation;

import com.mynotion.common.SuccessResponse;
import com.mynotion.oauth.application.OAuthService;
import com.mynotion.oauth.domain.OauthServerType;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth")
@RestController
public class OauthController {

    @Value("${jwt.refreshTokenExpirationMs}")
    private long refreshTokenExpirationMs;
    private final OAuthService oauthService;

    @SneakyThrows
    @GetMapping("/{oauthServerType}")
    ResponseEntity<Void> redirectAuthCodeRequestUrl(
            @PathVariable OauthServerType oauthServerType,
            HttpServletResponse response
    ) {
        String redirectUrl = oauthService.getAuthCodeRequestUrl(oauthServerType);
        response.sendRedirect(redirectUrl);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login/{oauthServerType}")
    public ResponseEntity<SuccessResponse<String>> login(
            @PathVariable OauthServerType oauthServerType,
            @RequestParam("code") String code,
            HttpServletResponse response
    ) {
        Map<String, String> tokens = oauthService.login(oauthServerType, code);
        if(tokens.containsKey("oauthId")){
            return ResponseEntity.ok(new SuccessResponse<>("S-02",tokens.get("oauthId"),"회원가입을 진행해주세요"));
        }

        String accessToken = tokens.get("accessToken");
        String refreshToken = tokens.get("refreshToken");

        // 쿠키 생성
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge((int) TimeUnit.MILLISECONDS.toSeconds(refreshTokenExpirationMs));
        refreshTokenCookie.setPath("/");
        response.addCookie(refreshTokenCookie);

        SuccessResponse<String> successResponse = new SuccessResponse<>("S-01", accessToken, "로그인에 성공했습니다.");
        return ResponseEntity.ok(successResponse);
    }
}