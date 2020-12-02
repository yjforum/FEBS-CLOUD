package cc.michael.febs.server.rabbit.producer.confirm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 消息发送成功的回调
 * 需要开启
 * # 开启发送确认
 * publisher-confirms: true
 *
 * @author michael
 * @date 2019-07-22 15:44
 **/
@Slf4j
public class RabbitConfirmCallBack
        implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("消息唯一标识: {}", correlationData);
        log.info("确认状态: {}", ack);
        log.info("造成原因: {}", cause);
        if (ack) {
            log.info("消息发送成功:{}", "SUCCESS");
        } else {
            log.info("消息发送失败:{}", "FAIL");
        }
    }
}
