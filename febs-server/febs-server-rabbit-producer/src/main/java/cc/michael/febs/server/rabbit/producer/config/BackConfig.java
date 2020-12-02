package cc.michael.febs.server.rabbit.producer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author michael
 * @date 2019-07-22
 **/
@Configuration
public class BackConfig {

    public static final String BACK_QUEUE1 = "back.queue1";
    public static final String BACK_QUEUE2 = "back.queue2";
    public static final String BACK_EXCHANGE = "back.exchange";
    public static final String BACK_EXCHANGE_NO = "back.exchange.no";

    @Bean
    public Queue getBackQueue() {
        return new Queue(BACK_QUEUE1);
    }

    @Bean
    public Queue getBackQueue2() {
        return new Queue(BACK_QUEUE2);
    }

    @Bean
    public DirectExchange getBackExchange() {
        return new DirectExchange(BACK_EXCHANGE);
    }

    /**
     * direct模式
     * 消息中的路由键（routing key）如果和 Binding 中的 binding key 一致，
     * 交换器就将消息发到对应的队列中。路由键与队列名完全匹配
     *
     * @return
     */
    @Bean
    public Binding backBinding1() {
        return BindingBuilder
                // 设置queue
                .bind(getBackQueue())
                // 绑定交换机
                .to(getBackExchange())
                // 设置routingKey
                .with("back.V1");
    }


    @Bean
    public Binding backBinding2() {
        return BindingBuilder
                // 设置queue
                .bind(getBackQueue2())
                // 绑定交换机
                .to(getBackExchange())
                // 设置routingKey
                .with("back.V2");
    }

}