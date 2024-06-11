package org.delivery.api.domain.user.controller;


import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.model.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

//로그인된 사용자가 들어오는 컨트롤러
//jwt token 사용
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    private final UserBusiness userBusiness;

    @GetMapping("/me")
    public Api<UserResponse> me(
            @UserSession User user
    ){

       /* var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
        var userId = requestContext.getAttribute("userId",RequestAttributes.SCOPE_REQUEST);
        var response = userBusiness.me(Long.parseLong(userId.toString()));
*/
        var response = userBusiness.me(user);
        return Api.OK(response);
    }
}

/*
컨트롤러로 사용자 요청이 들어오면
컨트롤러 -> 비즈니스 -> 서비스 -> 레포지토리
서비스는 해당 도메인 로직만 처리
비즈니스는 결합 해도 됨, 여러가지 서비스 처리
컨트롤러 : 헤더, 리스폰스에 대한 데이터 변환
 */