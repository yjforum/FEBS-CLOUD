package cc.michael.febs.server.rabbit.producer.direct;

import cc.michael.febs.server.rabbit.producer.config.BackConfig;
import cc.michael.febs.server.rabbit.producer.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BackSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send1(User user) {
        this.rabbitTemplate.convertAndSend(
                BackConfig.BACK_EXCHANGE,
                // routingKey
                "back.V1",
                user);
    }

    public void send2(User user) {
        this.rabbitTemplate.convertAndSend(
                BackConfig.BACK_EXCHANGE_NO,
                // routingKey
                "back.V2",
                user);
    }

    public void send3(User user) {
        this.rabbitTemplate.convertAndSend(
                BackConfig.BACK_EXCHANGE,
                // routingKey
                "back.V3",
                user);
    }
}
