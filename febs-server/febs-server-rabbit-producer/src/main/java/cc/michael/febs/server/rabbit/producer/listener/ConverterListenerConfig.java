package cc.michael.febs.server.rabbit.producer.listener;

import cc.michael.febs.server.rabbit.producer.util.MessageConverterUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@Slf4j
public class ConverterListenerConfig {
    @Value("${listenerQueue.queues}")
    private String listenerQueue;

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new MessageConverterUtil());
        return template;
    }

    /**
     * 防止消息丢失方案和防止消息重复消费 1、消息持久化 2、消息确认机制  如果消费者消费消息出现异常或网络中断，那么消息确认机制就不会给MQ反馈，然后MQ就会把消费失败的消息重新加入到消息队列中
     * 如果消息消费失败，还应该设置消息重试机制，如果到达重试机制阈值，那么就要把消息丢弃掉，防止内存泄漏
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        if (StringUtils.isEmpty(listenerQueue)) {
            log.info("没有配置要监控的RabbitMQ队列");
            return null;
        } else {
            SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
            container.setConnectionFactory(connectionFactory);
            String[] str = listenerQueue.split(",");
            for (String s : str) {
                container.addQueueNames(s);
            }
            //需要将channel暴露给listener才能手动确认，AcknowledgeMode.MANUAL时必须为ture
            container.setExposeListenerChannel(true);
            //并发消费设置最大消费者数量，并发消费的时候需要设置，且>=concurrentConsumers
            container.setMaxConcurrentConsumers(1);
            //设置默认的当前的消费者数量
            container.setConcurrentConsumers(1);
            //设置手动确认
            container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
            //container.setDefaultRequeueRejected(false);//当消费消息抛出异常没有catch住时，这条消息会被rabbitmq放回到queue头部，再被推送过来，然后再抛异常再放回…死循环了。设置false的作用是抛异常时不放回，而是直接丢弃
            //设置listener
            container.setMessageListener(new ChannelAwareMessageListener() {
                @Override
                public void onMessage(Message message, Channel channel) throws Exception {
                    byte[] body = message.getBody();
                    String content = new String(body);
                    String routing = message.getMessageProperties().getReceivedRoutingKey();
                    log.info("开始处理消息：" + routing + "-------------msg" + content);
                    try {
                        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);//确认成功消费消息
                        log.info("消息处理结束：" + routing + "-------------msg" + content);
                    } catch (Exception e) {
                        log.info("异常信息:", e);
                    }
                }
            });
            return container;
        }
    }
}
