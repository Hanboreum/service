package org.delivery.api.config.web;

import lombok.RequiredArgsConstructor;
import org.delivery.api.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

//Authorization Interceptor 설정
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {


    private final AuthorizationInterceptor authorizationInterceptor;

    private List<String> OPEN_API = List.of(
            "/open-api/**"
                    //위 주소는 검증하지 않음
    );

    private List<String> DEFAULT_EXCLUDE = List.of(
            "/",
            "favicon.ico",
            "/error"
    );

    private List<String> SWAGGER = List.of(
            "/swagger-ui.html", //http://localhost:8080/swagger-ui/index.html
            "/swagger-ui/**",
            "/v3/api-docs/**"
    );

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
                .excludePathPatterns(OPEN_API)
                .excludePathPatterns(DEFAULT_EXCLUDE)
                .excludePathPatterns(SWAGGER);

    }

}

/*
인증을 받을 것, 인증은 유저들만 사용하는 api
인증 못하면 api통과 하면 안됨.

회원가입, 약관같은 인증 안타는 api는? =  .excludePathPatterns("?api/user/reigster")
하지만 불편

해결책 : 위 코드

 */