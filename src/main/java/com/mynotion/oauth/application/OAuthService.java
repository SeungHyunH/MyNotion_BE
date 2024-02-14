package com.mynotion.oauth.application;

import com.mynotion.jwt.JwtProvider;
import com.mynotion.oauth.domain.OAuth;
import com.mynotion.oauth.domain.OAuthRepository;
import com.mynotion.oauth.domain.OauthServerType;
import com.mynotion.oauth.domain.authcode.AuthCodeRequestUrlProviderComposite;
import com.mynotion.oauth.domain.client.OauthClientComposite;
import com.mynotion.user.model.User;
import com.mynotion.user.model.UserDto;
import com.mynotion.user.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;
    private final OauthClientComposite oauthClientComposite;
    private final OAuthRepository oAuthRepository;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public String getAuthCodeRequestUrl(OauthServerType oauthServerType) {
        return authCodeRequestUrlProviderComposite.provide(oauthServerType);
    }

    public Map<String, String> login(OauthServerType oauthServerType, String authCode ){
        OAuth existingOAuth = oauthClientComposite.fetch(oauthServerType, authCode);
        OAuth saved = oAuthRepository.findByProviderUserId(existingOAuth.getProviderUserId())
                .orElseGet(() -> oAuthRepository.save(existingOAuth));
        System.out.println(saved.getOauthId());
        Optional<User> existingUser = userRepository.findById(saved.getOauthId());
        if(existingUser.isEmpty()){
            HashMap<String,String> res = new HashMap<>();
            res.put("oauthId",saved.getOauthId().toString());
            return res;
        }
        return generateTokens(existingUser.get().getUsername());
    }

    public Map<String, String> generateTokens(String username) {
        String accessToken = jwtProvider.generateToken(username);
        String refreshToken = jwtProvider.generateRefreshToken(username);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken",refreshToken);

        return tokens;
    }
}
