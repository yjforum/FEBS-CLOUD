package cc.michael.febs.server.rabbit.producer.util;

public class RabbitConstant {
    //direct发布/订阅模式的交换机和消息队列
    public static final String DIRECT_EXCHANDE_NAME = "direct.exchange";
    public static final String DIRECT_ROUTING_KEY = "direct.routing";

    //fanout广播模式的交换机和消息队列
    public static final String FANOUT_EXCHANDE_NAME = "fanout.exchange";
    public static final String FANOUT_ROUTING_KEY = "fanout.routing";

    //topic主题/规则模式的交换机和消息队列
    public static final String TOPIC_EXCHANDE_NAME = "topic.exchange";
    public static final String TOPIC_ROUTING_KEY = "topic.routing";

}
