package org.delivery.storeadmin.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.db.userorder.UserOrderRepository;
import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import org.delivery.storeadmin.domain.userorder.service.UserOrderService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserOrderBusiness {

    private final UserOrderRepository userOrderRepository;
    private final SseConnectionPool sseConnectionPool;
    private final UserOrderService userOrderService;


    /**
     * 주문
     * 주문 내역 찾기
     * 스토어 찾기
     * 연결된 세션 찾아 push
     */
    public void pushUserOrder(UserOrderMessage userOrderMessage){

        var userOrderEntity = userOrderService.getUserOrder(userOrderMessage.getUserOrderId()).orElseThrow(
            () -> new RuntimeException("사용자 주문 내역 없음"));

        //사용자 주문이 들어왔다면 user order entity

        //user order menu

        //user order menu -> store menu

        //response

        //push

        //사용자 주문 찾았으면 올바른 데이터 형태 만들기
        //가게와 연겯된 connection 찾기
        var userConnection = sseConnectionPool.getSession(userOrderEntity.getStoreId().toString());


        //주문 메뉴, 가격, 상태
        //사용자에게 push
        userConnection.sendMessage();
    }
}
