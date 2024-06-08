package org.delivery.storeadmin.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.db.userorder.UserOrderRepository;
import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import org.delivery.storeadmin.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.storeadmin.domain.storemenu.service.StoreMenuService;
import org.delivery.storeadmin.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.storeadmin.domain.userorder.converter.UserOrderConverter;
import org.delivery.storeadmin.domain.userorder.service.UserOrderService;
import org.delivery.storeadmin.domain.userordermenu.service.UserOrderMenuService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final UserOrderConverter userOrderConverter;
    private final SseConnectionPool sseConnectionPool;

    private final UserOrderMenuService userOrderMenuService;

    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;


    /**
     * 주문
     * 주문 내역 찾기
     * 스토어 찾기
     * 연결된 세션 찾아 push
     *  push 가 오면 user order message 를 사용해 store를 찾고 어떤 메뉴인지 정확하게 분별하기 위해 order menu 를 통해 스토어의 메뉴와 이름을 가져온다.
     *  그 다음 하나의 객체 push를 만들고 push를 사용자한테 전송하면 끝
     */
    public void pushUserOrder(UserOrderMessage userOrderMessage){

        var userOrderEntity = userOrderService.getUserOrder(userOrderMessage.getUserOrderId()).orElseThrow(
            () -> new RuntimeException("사용자 주문 내역 없음"));

        //사용자 주문이 들어왔다면 user order entity

        //user order menu
        var userOrderMenuList = userOrderMenuService.getUserOrderMenuList(userOrderEntity.getId());


        //user order menu -> store menu. 데이터 변환
        var storeMenuResponseList = userOrderMenuList.stream()
            .map(userOrderMenuEntity->{
                return storeMenuService.getStoreMenuThrow(userOrderMenuEntity.getStoreMenuId());
            })
            .map(storeMenuEntity -> {  //response 1
                return storeMenuConverter.toResponse(storeMenuEntity);
            })
            .collect(Collectors.toList());

        //response2. list와 user order를 response 로 변환
        var userOrderResponse = userOrderConverter.toResponse(userOrderEntity);

        //response 1,2를 하나로 push
        var push = UserOrderDetailResponse.builder()
            .userOrderResponse(userOrderResponse)
            .storeMenuResponseList(storeMenuResponseList)
            .build();


        //사용자 주문 찾았으면 올바른 데이터 형태 만들기
        //가게와 연겯된 connection 찾기
        var userConnection = sseConnectionPool.getSession(userOrderEntity.getStoreId().toString());

        //주문 메뉴, 가격, 상태
        //사용자에게 push
        userConnection.sendMessage(push);
    }
}
