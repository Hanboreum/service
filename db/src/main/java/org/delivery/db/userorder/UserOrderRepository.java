package org.delivery.db.userorder;

import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserOrderRepository extends JpaRepository<UserOrderEntity, Long> {

    //특정 유저의 모든 주문
    //select * from user_order where user_id=[] and status=[] order by id desc;
    List<UserOrderEntity> findAllByUserIdAndStatusOrderByIdDesc(Long userId, UserOrderStatus status);

    //select * from user_order where user_id=[] and status in (?,?,? ...) order by id desc;
    //여러개 select ?
    List<UserOrderEntity> findAllByUserIdAndStatusInOrderByIdDesc(Long userId, List<UserOrderStatus> status);

    //특정 주문, 특정 id를 찍고 들어왔을 때 해당 주문이 유효한지, 특정 유저의 것이 맞는지
    //select * from user_id where id= [] and status =[] and user_id;
    Optional<UserOrderEntity> findAllByIdAndStatusAndUserId(Long id, UserOrderStatus status, Long userId);

    Optional<UserOrderEntity>findAllByIdAndUserId(Long id, Long userId);

}
