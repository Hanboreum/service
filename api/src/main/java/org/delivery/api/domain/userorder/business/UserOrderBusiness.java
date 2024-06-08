package org.delivery.api.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.delivery.api.domain.userorder.producer.UserOrderProducer;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.userorder.UserOrderEntity;

import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final StoreMenuService storeMenuService;
    private final UserOrderConverter userOrderConverter;
    private final UserOrderMenuConverter userOrderMenuConverter;
    private final UserOrderMenuService userOrderMenuService;
    private final StoreService storeService;
    private final StoreMenuConverter storeMenuConverter;
    private final StoreConverter storeConverter;
    private final UserOrderProducer userOrderProducer;

    //1. 사용자, 메뉴 아이디
    //2. userOrder 생성
    //3. userOrderMenu 생성 (주문자와 메뉴 맵핑 테이블)
    //4. 응답 생성
    public UserOrderResponse userOrder(User user,  UserOrderRequest body) {
        var storeMenuEntityList = body.getStoreMenuIdList()
            .stream()
            .map(it -> storeMenuService.getStoreMenuWithThrow(it))
            .collect(Collectors.toList());

        var userOrderEntity = userOrderConverter.toEntity(user, body.getStoreId(), storeMenuEntityList);

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

        //주문 내역 기록 남기기
        userOrderMenuEntityList.forEach(it -> {
            userOrderMenuService.order(it);
        });

        //비동기로 가맹점에 주문 알리기 (만들어둔것과 연결)
        userOrderProducer.sendOrder(newUserOrderEntity);

        //response
        return userOrderConverter.toResponse(newUserOrderEntity);
    }


    // 사용자의 현재 주문 내역
    public List<UserOrderDetailResponse> current(User user) {

        //주문건을 불러옴
        var userOrderEntityList = userOrderService.current(user.getId());

        //주문 한 건 씩 처리
        var userOrderDetailResponseList = userOrderEntityList.stream().map(it -> {

                //사용자가 주문한 메뉴, userOrderMenu를 통해 storeMenuEntityList를 찾아옴
                var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(it.getId());

                // it ==storeMenuEntity 어떤 메뉴를 주문했는지 나옴
                var storeMenuEntityList = userOrderMenuEntityList.stream()
                    .map(userOrderMenuEntity -> {
                        var storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                        return storeMenuEntity;
                    })
                    .collect(Collectors.toList());

                //사용자가 주문한 스토어, 어디서 주문 했는지 찾아온다 TODO 리팩토링 필요
                var storeEntity = storeService.getStoreEntityWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

                return UserOrderDetailResponse.builder()
                    .userOrderResponse(userOrderConverter.toResponse(it))
                    .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                    .storeResponse(storeConverter.toResponse(storeEntity))
                    .build();

            })
            .collect(Collectors.toList());

        return userOrderDetailResponseList;
    }

    //n건 가져오기
    public List<UserOrderDetailResponse> history(User user) {

        var userOrderEntityList = userOrderService.history(user.getId());

        var userOrderDetailList = userOrderEntityList.stream().map(it -> {
            var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(it.getId());
            var storeMenuEntityList = userOrderMenuEntityList.stream()
                .map(userOrderMenuEntity -> {
                    var storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                    return storeMenuEntity;
                })
                .collect(Collectors.toList());

            var storeEntity = storeService.getStoreEntityWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

            return UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderConverter.toResponse(it))
                .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                .storeResponse(storeConverter.toResponse(storeEntity))
                .build();
        }).collect(Collectors.toList());

        return userOrderDetailList;
    }

    //한 건 가져오기
    public UserOrderDetailResponse read(User user, Long orderId) {
        var userOrderEntity = userOrderService.getUserOrderIWithoutStatusWithThrow(orderId, user.getId());

        //사용자가 주문한 메뉴
        var userOrderEntityList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());
        var storeMenuEntityList = userOrderEntityList.stream()
            .map(userOrderMenuEntity -> {
                var storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                return storeMenuEntity;
            })
            .collect(Collectors.toList());

        //사용자가 주문한 스토어
        var storeEntity = storeService.getStoreEntityWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

        return UserOrderDetailResponse.builder()
            .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
            .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
            .storeResponse(storeConverter.toResponse(storeEntity))
            .build();
    }
}
