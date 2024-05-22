package org.delivery.api.domain.user.business;


import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.business.TokenBusiness;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.user.controller.model.UserLoginRequest;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.converter.UserConverter;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.user.service.UserService;


@Business
@RequiredArgsConstructor
public class UserBusiness {

    private final UserService userService;
    private final UserConverter userConverter;
    private final TokenBusiness tokenBusiness;

    /**
     * 사용자에 대한 가입 처리 로직
     * 1. request -> entity 변환
     * 2. entity -> save
     * 3. save entity -> response
     * 4. response return
     */
    //리턴은 userResponse 로 통일 (?
    public UserResponse register(UserRegisterRequest request) { //req 받고

        //데이터 변환
        var entity = userConverter.toEntity(request);
        var newEntity = userService.register(entity);
        var response = userConverter.toResponse(newEntity);
        return response;

       /* return Optional.ofNullable(request)
                .map(userConverter::toEntity)
                .map(userService::register)
                .map(userConverter::toResponse)
                .orElseThrow(() ->new ApiException(ErrorCode.NULL_POINT,"Request null"));
    */
    }

    /**
     * 1.email, password로 회원 체크
     * 2. user entity 로그인 확인
     * 3. 토근 확인
     * 4. 토큰 response
     * @param request
     */
    public TokenResponse login(UserLoginRequest request) {

        //사용자 없으면 throw
        var userEntity = userService.login(request.getEmail(), request.getPassword());

        //토큰 생성 로직
        var tokenResponse = tokenBusiness.issueToken(userEntity);

        return tokenResponse;
    }

    public UserResponse me(User user) {
        var userEntity = userService.getUserWithThrow(user.getId());
        var response = userConverter.toResponse(userEntity);
        return response;
    }
}
