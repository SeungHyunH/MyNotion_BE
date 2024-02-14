package com.mynotion.oauth.domain.client;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.mynotion.oauth.domain.OAuth;
import com.mynotion.oauth.domain.OauthServerType;
import org.springframework.stereotype.Component;

@Component
public class OauthClientComposite {

    private final Map<OauthServerType, OauthClient> mapping;

    public OauthClientComposite(Set<OauthClient> clients) {
        mapping = clients.stream()
                .collect(toMap(
                        OauthClient::supportServer,
                        identity()
                ));
    }

    public OAuth fetch(OauthServerType oauthServerType, String authCode) {
        return getClient(oauthServerType).fetch(authCode);
    }

    private OauthClient getClient(OauthServerType oauthServerType) {
        return Optional.ofNullable(mapping.get(oauthServerType))
                .orElseThrow(() -> new RuntimeException("지원하지 않는 소셜 로그인 타입입니다."));
    }
}