package com.mynotion.oauth.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "oauth")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class OAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oauth_id")
    private Long oauthId;

    private String provider;

    @Column(name = "provider_user_id")
    private String providerUserId;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

}
