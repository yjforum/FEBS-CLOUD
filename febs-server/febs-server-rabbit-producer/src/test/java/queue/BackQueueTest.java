package queue;


import cc.michael.febs.server.rabbit.producer.FebsServerRabbitProducerApplication;
import cc.michael.febs.server.rabbit.producer.direct.BackSender;
import cc.michael.febs.server.rabbit.producer.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 该测试类是属于一个队列对应一个交换机
 * Direct交换器
 * Producer测试。
 * 注意：
 * 要执行该测试类里面的方法需要把该项目的RabbitConfig类和ConverterListenerConfig注释掉，要不然会报错
 * 在rabbitmq中，consumer都是listener监听模式消费消息的。
 * 一般来说，在开发的时候，都是先启动consumer，确定有什么exchange、queue、routing-key，然后再启动producer。
 * 然后再启动producer发送消息，。
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FebsServerRabbitProducerApplication.class)
public class BackQueueTest {
    @Autowired
    private BackSender backSender;


    /*
     * 测试direct 发布/订阅模式消息队列
     */
    @Test
    public void send1() throws InterruptedException {
        User u = new User();
        u.setId(2);
        u.setName("send1");
        u.setAge("27");
        backSender.send1(u);
    }

    /*
     * 测试direct 发布/订阅模式消息队列
     */
    @Test
    public void send2() {
        User u = new User();
        u.setId(1);
        u.setName("send2");
        u.setAge("27");
        backSender.send2(u);
    }

    /*
     * 测试direct 发布/订阅模式消息队列
     */
    @Test
    public void send3() {
        User u = new User();
        u.setId(1);
        u.setName("send3");
        u.setAge("27");
        backSender.send3(u);
    }


}
