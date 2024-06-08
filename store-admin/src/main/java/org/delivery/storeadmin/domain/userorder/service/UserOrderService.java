package org.delivery.storeadmin.domain.userorder.service;

import lombok.RequiredArgsConstructor;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userorder.UserOrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;

    //주문 들어왔을 때 해당 주문 리턴
    public Optional<UserOrderEntity> getUserOrder(Long id){
        return userOrderRepository.findById(id);
    }
}
