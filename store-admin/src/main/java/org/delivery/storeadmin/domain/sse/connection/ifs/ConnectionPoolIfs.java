package org.delivery.storeadmin.domain.sse.connection.ifs;


public interface ConnectionPoolIfs<T,R> {

    //key, 저장할 session
    void addSession(T uniqueKey, R session);

    //key 가져오면 리턴
    R getSession(T uniqueKey);

    void onCompletionCallBack(R session);
}
