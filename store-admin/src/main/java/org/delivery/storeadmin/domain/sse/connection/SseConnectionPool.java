package org.delivery.storeadmin.domain.sse.connection;

import lombok.extern.slf4j.Slf4j;
import org.delivery.storeadmin.domain.sse.connection.ifs.ConnectionPoolIfs;
import org.delivery.storeadmin.domain.sse.connection.model.UserSseConnection;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
//ConnectionPool은 Bean으로 관리돼 하나만 존재하는 static 객체
public class SseConnectionPool implements ConnectionPoolIfs<String, UserSseConnection> { //key,session
    //session 에서 가져오고 내보내는 게 필요 -> ConnectionPoolIfs 생성

    private static final Map<String, UserSseConnection> connectionPool = new ConcurrentHashMap<>();

    @Override
    public void addSession(String uniqueKey, UserSseConnection userSseConnection) {
        connectionPool.put(uniqueKey, userSseConnection);
    }

    @Override
    public UserSseConnection getSession(String uniqueKey) {
        return connectionPool.get(uniqueKey);
    }


    //userConnection에서 connectionpool 을 호출 가능해야함 -> callback method 사용
    @Override
    public void onCompletionCallBack(UserSseConnection session) {
        log.info("call back connection pool completion : {}", session);
        connectionPool.remove(session.getUniqueKey());
    }

}
