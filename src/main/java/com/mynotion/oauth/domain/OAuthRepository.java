package com.mynotion.oauth.domain;

import com.mynotion.oauth.domain.OAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthRepository extends JpaRepository<OAuth, Long> {

    Optional<OAuth> findByProviderUserId(String providerUserId);
}