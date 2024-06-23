package org.delivery.api.common.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
//rabbit template 을 사용해 보내준다.
@RequiredArgsConstructor
@Component
public class Producer {

    private final RabbitTemplate rabbitTemplate;

    public void producer(String exchange, String routeKey, Object object){
        //rabbitTemplate을 통해 exchange에 routekey를 통해 object를 보낸다.
        rabbitTemplate.convertAndSend(exchange, routeKey, object);
    }
}
