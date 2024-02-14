package com.mynotion.oauth.infra.oauth.naver.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mynotion.oauth.domain.OAuth;

import static com.mynotion.oauth.domain.OauthServerType.NAVER;

@JsonNaming(value = SnakeCaseStrategy.class)
public record NaverMemberResponse(
        String resultcode,
        String message,
        Response response
) {

    public OAuth toDomain() {
        return OAuth.builder()
                .provider("NAVER")
                .providerUserId(response.id())
                .profileImageUrl(response.profileImage())
                .build();
    }

    @JsonNaming(value = SnakeCaseStrategy.class)
    public record Response(
            String id,
            String nickname,
            String name,
            String email,
            String gender,
            String age,
            String birthday,
            String profileImage,
            String birthyear,
            String mobile
    ) {
    }
}
