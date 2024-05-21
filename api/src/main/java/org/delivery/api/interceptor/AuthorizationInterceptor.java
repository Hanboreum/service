package org.delivery.api.interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

//클아이언트
@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Authorization Interceptor url : {}" , request.getRequestURL());

        //web, chrome의 경우 get,post option 통과 시키기
        if(HttpMethod.OPTIONS.matches(request.getMethod())){
            return true;
        }

        //resource검증 : js.html, png resource 를 요청하는 경우 통과
        if(handler instanceof ResourceHttpRequestHandler){
            return true;
        }

        //TODO header 검증

        return true; //일단 통과
    }
}
