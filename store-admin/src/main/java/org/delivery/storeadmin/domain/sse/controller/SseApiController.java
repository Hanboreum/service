package org.delivery.storeadmin.domain.sse.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sse")
public class SseApiController {
    //emitter 를 userConnection 에 저장. 새로운 요청이 들어오면 connection 에서 꺼내 발송
    private static final Map<String, SseEmitter>  userConnection = new ConcurrentHashMap<>(); //thread safe한 자료구조

    @GetMapping(path = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseBodyEmitter connect(
        @Parameter(hidden = true)
        @AuthenticationPrincipal UserSession userSession
    ) {
        log.info("Login user : {}", userSession);

        //클라이언트와 연결된 sse 연결이 이 떄 성립된다.
        var emitter = new SseEmitter(1000L * 60);
        userConnection.put(userSession.getUserId().toString(), emitter);

        //연결시 이벤트
        emitter.onTimeout(() -> {
            log.info(" on time out");
            //클라이언트와 타임아웃이 일어났을 때, 종료 호춯
            emitter.complete(); //1.연결이 일어나면
        });

        //클라이언트와 연결 종료시
        emitter.onCompletion(() -> {
            log.info("on completion");

            //클라이언트와 연결이 종료됐을 때 하는 작업. 지워준다.
            userConnection.remove(userSession.getUserId().toString());

        });

        //최초 연결시 응갑 전송 //2.응답 전송
        var event = SseEmitter
            .event()
            .name("onopen")
            .data("connect");


        try {
            emitter.send(event); //3. 메세지로 onopen connect 전송
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
        return emitter;
    }

    @GetMapping("/push-event")
    public void pushEvent(
        @Parameter(hidden = true)
        @AuthenticationPrincipal UserSession userSession
    ) {
        //기존에 연결된 유저 찾기
        var emitter =userConnection.get(userSession.getUserId().toString());

        //메세지 보내기
        var event = SseEmitter
            .event()
            .data("hello") //자동으로 onmessage에 전달된다
            ;

        try {
            emitter.send(event);
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
    }
}
