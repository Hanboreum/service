package org.delivery.api.domain.token.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.token.converter.TokenConverter;
import org.delivery.api.domain.token.service.TokenService;
import org.delivery.db.user.UserEntity;

import java.util.Optional;

@Business
@RequiredArgsConstructor
//토큰 발행
public class TokenBusiness {

    private final TokenService tokenService;
    private final TokenConverter tokenConverter;

    /**
     * 1. user entity -user id추출
     * 2. access, refreash token 발행
     * 3. converter -> token response 로 변경
     * token response를 만든ㄷ다.
     */
    public TokenResponse issueToken(UserEntity userEntity){
        return Optional.ofNullable(userEntity)
                .map(ue->{

                    return userEntity.getId();

                })
                .map(userId ->{

                    //userid로 토큰 발행
                    var accessToken = tokenService.issueAccessToken(userId);
                    var refreshToken = tokenService.issueRefreashToken(userId);
                    return tokenConverter.toResponse(accessToken,refreshToken);

                })
                .orElseThrow(
                        ()-> new ApiException(ErrorCode.NULL_POINT)
                );

    }

    public Long validationAccessToken(String accessToken){
        var userId = tokenService.validationToken(accessToken);
        return userId;
    }
}
