package org.delivery.api.resolver;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.user.service.UserService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
//인터셉터 -> 리졸버
//User, UserSession, UserSessionResolver - WebConfig
@Component
@RequiredArgsConstructor
public class UserSessionResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //지원하는 파라미터 , 어노테이션 체크

        //1. 어노테이션 유무 체크
        var annotation = parameter.hasParameterAnnotation(UserSession.class);
        //2. 파라미터 타입 체크, 매개변수로 들어온 parameter 가 User class 가 맞는지 확인
        var parameterType = parameter.getParameterType().equals(User.class);

        return (annotation && parameterType == true);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
       //supportsParameter 을 만족한다면 이곳으로 이동

        //request context holder 에서 user id 찾아오기
        var requestContext = RequestContextHolder.getRequestAttributes();
        //requestContext 에서 userEntity 찾아오기
        var userId = requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);

        //userid 찾아서 entity 로 반환
        var userEntity = userService.getUserWithThrow(Long.parseLong(userId.toString()));


        //user에 entity 내용을 넣어줌
        // 사용자 정보 세팅. 해당 메서드에서 활용할 유저 정보를 담는다.
        //비즈니스 로직서 더 이상 헤더 참조를 해 유저 아이디 꺼내 유저가 유효한지 확인할 필요가 없다.
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .status(userEntity.getStatus())
                .address(userEntity.getAddress())
                .registeredAt(userEntity.getRegisteredAt())
                .unRegisteredAt(userEntity.getUnRegisteredAt())
                .lastLoginAt(userEntity.getLastLoginAt())
                .build();

    }
}
/*
resolver 에서 인증-> controller 에서 사용자 가져옴
 */