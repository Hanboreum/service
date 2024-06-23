package org.delivery.api.domain.userorder.producer;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.rabbitmq.Producer;
import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.db.userorder.UserOrderEntity;
import org.springframework.stereotype.Service;
//rabbitmq 주문하기
@Service
@RequiredArgsConstructor
public class UserOrderProducer {

    private final Producer producer;

    private static final String EXCHANGE = "delivery.exchange";
    private static final String ROUTE_KEY = "delivery.key";


    //주문 보내기
    public void sendOrder(UserOrderEntity userOrderEntity){
        sendOrder(userOrderEntity.getId());
    }
    public void sendOrder(Long userOrderId){
        //common class
        var message = UserOrderMessage.builder()
            .userOrderId(userOrderId)
            .build();
        //사용자가 주문을 하게 되면 userorderentity or userorderid를 넣어 메세지를 만들어 queue에 집어넣는 producer
        producer.producer(EXCHANGE, ROUTE_KEY, message);
    }
}
