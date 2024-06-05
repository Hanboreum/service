package org.delivery.api.config.health;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.api.common.rabbitmq.Producer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api")
@Slf4j
public class HealthOpenApiController {

    //private final Producer producer;

    @GetMapping("/health")
    public void health(){
        log.info("health call");
        //producer.producer("delivery.exchange", "delivery.routeKey", "hello");
    }
}
