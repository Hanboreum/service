package org.delivery.api.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;

import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final StoreMenuService storeMenuService;
    private final UserOrderConverter userOrderConverter;
    private final UserOrderMenuConverter userOrderMenuConverter;
    private final UserOrderMenuService userOrderMenuService;


    //1. 사용자, 메뉴 아이디
    //2. userOrder 생성
    //3. userOrderMenu 생성 (주문자와 메뉴 맵핑 테이블)
    //4. 응답 생성
    public UserOrderResponse userOrder(User user, UserOrderRequest body) {
        var storeMenuEntityList = body.getStoreMenuIdList()
            .stream()
            .map(it -> storeMenuService.getStoreMenuWithThrow(it))
            .collect(Collectors.toList());

        var userOrderEntity = userOrderConverter.toEntity(user, storeMenuEntityList);

        //주문
        var newUserOrderEntity = userOrderService.order(userOrderEntity);

        //맵핑, store menu 별로 만들어 줘야하기 때문에 List
        var userOrderMenuEntityList = storeMenuEntityList.stream()
            .map(it -> {
                //menu + userOrder
                var userOrderMenuEntity = userOrderMenuConverter.toEntity(
                    newUserOrderEntity, it);

                return userOrderMenuEntity;
            })
            .collect(Collectors.toList());

        //주문 내역 남기기
        userOrderMenuEntityList.forEach(it -> {
            userOrderMenuService.order(it);
        });
        //response
        return userOrderConverter.toResponse(newUserOrderEntity);
    }


}
