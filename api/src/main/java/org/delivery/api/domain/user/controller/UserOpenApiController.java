package org.delivery.api.domain.user.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.controller.model.UserLoginRequest;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RequiredArgsConstructor
@RestControllerAdvice
@RequestMapping("/open-api/user")
public class UserOpenApiController {

    private final UserBusiness userBusiness;

    //가입, userresponse 리턴해서
    @PostMapping("/register")
    public Api<UserResponse> register(
            @Valid
            @RequestBody Api<UserRegisterRequest> request //1. req가 들어온다, valid검증 완료 후
    ){
        var response = userBusiness.register(request.getBody()); //2. 비즈스 로직에 전달
        return Api.OK(response);
    }

    //로그인
    @PostMapping("/login")
    public Api<UserResponse> login (
            @Valid
            @RequestBody Api<UserLoginRequest> request
    ){

        var response = userBusiness.login(request.getBody());
        return Api.OK(response);
    }

    //
}
