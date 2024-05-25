package org.delivery.api.domain.userorder.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderDetailResponse {

    //사용자가 주문한 건에 대한 응답
    private UserOrderResponse userOrderResponse;

    //가게 정보
    private StoreResponse storeResponse;

    //메뉴 정보 n건
    private List<StoreMenuResponse> storeMenuResponseList;

}
