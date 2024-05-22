package org.delivery.api.domain.token.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.token.model.TokenDto;

import java.util.Objects;

@Converter
@RequiredArgsConstructor
//데이터 변환
public class TokenConverter {

    public TokenResponse toResponse(
            TokenDto accessToken,
            TokenDto refreashToken
    ){
        //accessToken, refreashToken 이 null이라면 예외
        Objects.requireNonNull(accessToken,()->{ throw new ApiException(ErrorCode.NULL_POINT);});
        Objects.requireNonNull(refreashToken,()->{ throw new ApiException(ErrorCode.NULL_POINT);});

        return TokenResponse.builder()
                .accessToken(accessToken.getToken())
                .accessTokenExpiredAt(accessToken.getExpiredAt())
                .refreashToken(refreashToken.getToken())
                .refreashTokenExpiredAt(refreashToken.getExpiredAt())
                .build();
    }
}
