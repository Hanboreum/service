package org.delivery.api.domain.token.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.ifs.TokenHelperIfs;
import org.delivery.api.domain.token.model.TokenDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * 토큰에 대한 도메인 로직 처리
 *
 * 외부 라이브러리의 도움을 받는다.
 */

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenHelperIfs tokenHelperIfs;

    public TokenDto issueAccessToken(Long userId){
        var  data = new HashMap<String, Object>();
        data.put("userId", userId);
        return tokenHelperIfs.issueAccessToken(data);
    }

    public TokenDto issueRefreashToken(Long userId){
        var  data = new HashMap<String, Object>();
        data.put("userId", userId);
        return tokenHelperIfs.issueRefreashToken(data);

    }

    public Long validationToken(String token){
        var map = tokenHelperIfs.validationTokenWithThrow(token);
        var userId = map.get("userId");

        //userId 없을 때 예외처리
        Objects.requireNonNull(userId,()->{throw  new ApiException(ErrorCode.NULL_POINT);});

        return Long.parseLong(userId.toString());
    }
}
