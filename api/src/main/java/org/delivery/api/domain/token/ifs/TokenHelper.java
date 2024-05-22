package org.delivery.api.domain.token.ifs;

import org.delivery.api.domain.token.model.TokenDto;

import java.util.Map;

public interface TokenHelper {

    TokenDto issueAccessToken(Map<String, Object> data);

    TokenDto issueRefreashToken(Map<String, Object> data);

    Map<String, Object> validationTokenWithThrow(String token); //refreash, access 토큰이 될 수 있다.
}
