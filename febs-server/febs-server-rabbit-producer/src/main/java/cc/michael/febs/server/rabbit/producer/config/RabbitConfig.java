package cc.michael.febs.server.rabbit.producer.config;

import cc.michael.febs.server.rabbit.producer.util.RabbitConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.List;

/**
 * @author michael
 * @date 2019-07-22
 **/
@Configuration
@EnableRabbit
public class RabbitConfig {
    @Value("${listenerQueue.queues}")
    private String listenerQueue;
    List<Queue> list = new LinkedList<Queue>();

    /**
     * direct模式声明队列
     *
     * @return
     */
    @Bean
    public Queue queue() {
        //第一个参数是队列名，第二个参数是是否持久化，第三个参数是排他队列，这种队列适用于只限于一个客户端发送读取消息的应用场景，连接关闭后会自动删除
        // 第四个参数是是否自动删除，如果该队列没有任何订阅的消费者的话，该队列会被自动删除。这种队列适用于临时队列。
        return new Queue(listenerQueue);
    }//队列不持久化，连接关闭，队列消失

    /**
     * direct模式声明交换机
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        //第一个参数是交换机名，第二个参数是是否持久化，第三个参数是是否自动删除，true表示如果该交换机没有绑定队列的话就会自动删除
        return new DirectExchange(RabbitConstant.DIRECT_EXCHANDE_NAME, true, false);
    }

    /**
     * direct模式
     * 消息中的路由键（routing key）如果和 Binding 中的 binding key 一致，
     * 交换器就将消息发到对应的队列中。路由键与队列名完全匹配
     *
     * @return
     */
    @Bean
    public Binding binding() {
        return BindingBuilder
                // 设置queue
                .bind(queue())
                // 绑定交换机
                .to(directExchange())
                // 设置routingKey
                .with(RabbitConstant.DIRECT_ROUTING_KEY);
    }


}
