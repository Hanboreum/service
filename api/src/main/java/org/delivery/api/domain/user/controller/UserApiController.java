package org.delivery.api.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.db.user.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
@RequestMapping("/api/user")
public class UserApiController {

    private final UserBusiness userBusiness;




}

/*
컨트롤러로 사용자 요청이 들어오면
컨트롤러 -> 비즈니스 -> 서비스 -> 레포지토리
서비스는 해당 도메인 로직만 처리
비즈니스는 결합 해도 됨, 여러가지 서비스 처리
컨트롤러 : 헤더, 리스폰스에 대한 데이터 변환
 */