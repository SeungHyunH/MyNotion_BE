package com.mynotion.oauth.domain.authcode;

import com.mynotion.oauth.domain.OauthServerType;

public interface AuthCodeRequestUrlProvider {

    OauthServerType supportServer();

    String provide();
}