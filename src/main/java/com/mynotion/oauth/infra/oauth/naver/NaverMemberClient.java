package com.mynotion.oauth.infra.oauth.naver;


import com.mynotion.oauth.domain.OAuth;
import com.mynotion.oauth.domain.OauthServerType;
import com.mynotion.oauth.domain.client.OauthClient;
import com.mynotion.oauth.infra.oauth.naver.client.NaverApiClient;
import com.mynotion.oauth.infra.oauth.naver.dto.NaverMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import com.mynotion.oauth.infra.oauth.naver.dto.NaverToken;
@Component
@RequiredArgsConstructor
public class NaverMemberClient implements OauthClient {

    private final NaverApiClient naverApiClient;
    private final NaverOauthConfig naverOauthConfig;

    @Override
    public OauthServerType supportServer() {
        return OauthServerType.NAVER;
    }

    @Override
    public OAuth fetch(String authCode) {
        NaverToken tokenInfo = naverApiClient.fetchToken(tokenRequestParams(authCode));
        NaverMemberResponse naverMemberResponse = naverApiClient.fetchMember("Bearer " + tokenInfo.accessToken());
        return naverMemberResponse.toDomain();
    }

    private MultiValueMap<String, String> tokenRequestParams(String authCode) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", naverOauthConfig.clientId());
        params.add("client_secret", naverOauthConfig.clientSecret());
        params.add("code", authCode);
        params.add("state", naverOauthConfig.state());
        return params;
    }
}