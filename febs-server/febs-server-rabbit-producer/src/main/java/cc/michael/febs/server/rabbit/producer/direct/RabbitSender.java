package cc.michael.febs.server.rabbit.producer.direct;

import cc.michael.febs.server.rabbit.producer.entity.User;
import cc.michael.febs.server.rabbit.producer.util.RabbitConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send1(User user) {
        this.rabbitTemplate.convertAndSend(
                RabbitConstant.DIRECT_EXCHANDE_NAME,
                // routingKey
                RabbitConstant.DIRECT_ROUTING_KEY,
                user);
    }


}

