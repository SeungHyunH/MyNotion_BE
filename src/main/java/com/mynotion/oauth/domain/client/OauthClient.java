package com.mynotion.oauth.domain.client;

import com.mynotion.oauth.domain.OAuth;
import com.mynotion.oauth.domain.OauthServerType;


public interface OauthClient {

    OauthServerType supportServer();

    OAuth fetch(String code);
}
