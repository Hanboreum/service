package org.delivery.api.config.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory; //주의!
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter; //주의!
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
//producer -> exchange - queue - 가맹점 server consumer

    @Bean
    public DirectExchange directExchange() {
        //exchange 가 특정 데이터들을 라우팅 해줘야 함 -> queue를 만들어야 함
        return new DirectExchange ("delivery.exchange");
    }

    @Bean
    public Queue queue(){ //queue
        return new Queue("delivery.queue");
    }


    @Bean //exchange와 queue가 바인딩이 되도록 설정해줌
    public Binding binding(DirectExchange directExchange, Queue queue){
        return BindingBuilder.bind(queue).to(directExchange).with("delivery.key");

    }
    //end queue 설정


    //producer가 exchange에 필요한 데이터를 보내야 한다. 보낼때 프로토콜이 정해져 있다. = rabbitTemplate
    @Bean
    public RabbitTemplate rabbitTemplate( //보내기위해
        ConnectionFactory connectionFactory, //이건 어디서 bean으로 만드나 -yaml
         MessageConverter messageConverter
    ){
        var rabbitTemplate = new RabbitTemplate(connectionFactory); //connectionfactory로 부터 rabbit template만든다
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper){// 매개변수로 받게 되면 빈으로 등록된 objectmapper(objectmapperconfig)를 찾아온다
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
