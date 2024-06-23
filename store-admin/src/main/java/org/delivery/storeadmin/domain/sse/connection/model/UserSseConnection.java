package org.delivery.storeadmin.domain.sse.connection.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.delivery.storeadmin.domain.sse.connection.ifs.ConnectionPoolIfs;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Getter
//@Setter
@ToString
@EqualsAndHashCode
public class UserSseConnection {

    private final String uniqueKey;
    private final SseEmitter sseEmitter;
    private final ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs;

    private final ObjectMapper objectMapper;

    //UserConnection 은 사용자마자 만들어지는 객체
    //ConnectionPool은 Bean으로 관리돼 하나만 존재하는 static 객체
    //userConnection에서 connectionpool 을 호출 가능해야함 -> callback method 사용
    private UserSseConnection(
        String uniqueKey,
        ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs,
        ObjectMapper objectMapper //json 으로 보내기 위해
    ) {
        //key 초기화
        this.uniqueKey = uniqueKey;

        //sse 초기화
        this.sseEmitter = new SseEmitter(60 * 1000L);

        //callback 초기화
        this.connectionPoolIfs = connectionPoolIfs;

        //objectmapper 초기화. json
        this.objectMapper = objectMapper;

        // on completion
        this.sseEmitter.onCompletion(() -> {
            //connection pool 에서 지워주기
            this.connectionPoolIfs
                .onCompletionCallBack(this);
        });

        //on timeout
        this.sseEmitter.onTimeout(() -> {
            this.sseEmitter.complete();
        });


        //onopne 메세지
        this.sendMessage("onopen", "connect");

    }

    public static UserSseConnection connect(
        String uniqueKey,
        ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs,
        ObjectMapper objectMapper
    ) {
        return new UserSseConnection(uniqueKey, connectionPoolIfs, objectMapper);
    }


    public void sendMessage(String eventName, Object data) {

        try {
            var json = this.objectMapper.writeValueAsString(data);
            var event = SseEmitter.event()
                .name(eventName)
                .data(json);

            this.sseEmitter.send(event);
        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }
    }

    public void sendMessage(Object data) {
        try {
            var json = this.objectMapper.writeValueAsString(data);

            var event = SseEmitter.event()
                .data(json);
            this.sseEmitter.send(event);
        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }
    }
}