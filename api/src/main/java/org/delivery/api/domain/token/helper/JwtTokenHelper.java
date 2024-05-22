package org.delivery.api.domain.token.helper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.delivery.api.common.error.TokenErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.ifs.TokenHelperIfs;
import org.delivery.api.domain.token.model.TokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Component

//JwtTokenHelper = jwt를 통해 토큰을 만든다.
public class JwtTokenHelper implements TokenHelperIfs {

    //서명을 하기 위해선 서명을 위한 키가 필요하다
    @Value("${token.secret.key}")
    private String secretKey;

    @Value("${token.access-token.plus-hour}")
    private Long accessTokenPlusHour;

    @Value("${token.refresh-token.plus-hour}")
    private Long refreashTokenPlusHour;

    @Override
    public TokenDto issueAccessToken(Map<String, Object> data) {
        //만료 시간
        var expiredLocalDateTime = LocalDateTime.now().plusHours(accessTokenPlusHour);
        var expiredAt = Date.from(
                expiredLocalDateTime.atZone(ZoneId.systemDefault()
                ).toInstant());


        //서명키
        var key= Keys.hmacShaKeyFor(secretKey.getBytes());

        var jwtToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(data) //data setting
                .setExpiration(expiredAt)
                .compact(); //생성

        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expiredLocalDateTime)
                .build()
                ;
    }

    @Override
    public TokenDto issueRefreashToken(Map<String, Object> data) {
        //만료 시간
        var expiredLocalDateTime = LocalDateTime.now().plusHours(refreashTokenPlusHour);
        var expiredAt = Date.from(
                expiredLocalDateTime.atZone(ZoneId.systemDefault()
                ).toInstant());


        //서명키
        var key= Keys.hmacShaKeyFor(secretKey.getBytes());

        var jwtToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(data) //data setting
                .setExpiration(expiredAt)
                .compact(); //생성

        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expiredLocalDateTime)
                .build()
                ;

    }

    @Override
    public Map<String, Object> validationTokenWithThrow(String token) {

        //토큰 들어오면 키를 만들어준다
        var key = Keys.hmacShaKeyFor(secretKey.getBytes());

        var parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();


        try{
            var result =  parser.parseClaimsJws(token);
            return new HashMap<String, Object>(result.getBody());

        }catch (Exception e){
            
            if(e instanceof SignatureException){
                //토큰이 유효하지 않을 때
                throw  new ApiException(TokenErrorCode.INVALID_TOKEN,e);
                
            } else if (e instanceof ExpiredJwtException) {
                //만료된 토큰
                throw new ApiException(TokenErrorCode.EXPIRED_TOKEN,e);
                
            }else{
                //그 외 에러
                throw new ApiException(TokenErrorCode.TOKEN_EXCEPTION,e);
            }

        }

    }
}
