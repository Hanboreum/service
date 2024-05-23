package org.delivery.api.domain.storemenu.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.storemenu.business.StoreMenuBusiness;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store-menu")
public class StoreMenuApiController {
    //특정 가게
    private final StoreMenuBusiness storeMenuBusiness;

    @GetMapping("/search")
    public Api<List<StoreMenuResponse>> search(
            @RequestParam Long storeId
    ){
        var response = storeMenuBusiness.search(storeId);
        return Api.OK(response);
    }
}
