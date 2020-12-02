package queue;


import cc.michael.febs.server.rabbit.producer.FebsServerRabbitProducerApplication;
import cc.michael.febs.server.rabbit.producer.direct.BackSender;
import cc.michael.febs.server.rabbit.producer.direct.RabbitSender;
import cc.michael.febs.server.rabbit.producer.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 该测试类是属于多个队列对应一个交换机
 * Direct交换器
 * Producer测试。
 * 注意：
 * 在rabbitmq中，consumer都是listener监听模式消费消息的。
 * 一般来说，在开发的时候，都是先启动consumer，确定有什么exchange、queue、routing-key，然后再启动producer。
 * 然后再启动producer发送消息，。
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FebsServerRabbitProducerApplication.class)
public class RabbitQueueTest {
    @Autowired
    private RabbitSender rabbitSender;


    /*
     * 测试direct 发布/订阅模式消息队列
     */
    @Test
    public void send1() throws InterruptedException {
        User u = new User();
        u.setId(1);
        u.setName("send2");
        u.setAge("27");
        rabbitSender.send1(u);
    }


}
