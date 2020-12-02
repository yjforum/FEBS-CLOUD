package cc.michael.febs.server.rabbit.producer.util;

import cc.michael.febs.server.rabbit.producer.entity.User;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

@Slf4j
public class MessageConverterUtil implements MessageConverter {

    /**
     * 将java对象和属性对象转换成Message对象
     *
     * @param o
     * @param messageProperties
     * @return
     * @throws MessageConversionException
     */
    @Override
    public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
        log.info("=======toMessage=========");
        String s = null;
        if (o instanceof User) {
            s = JSON.toJSONString(o);
        } else {
            s = o.toString();
        }
        return new Message(s.getBytes(), messageProperties);
    }

    /**
     * 将消息对象转换成java对象。
     *
     * @param message
     * @return
     * @throws MessageConversionException
     */
    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        log.info("=======fromMessage=========");
        String content = null;
        try {
            content = new String(message.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (content == null) {
            //都没有符合，则转换成字节数组
            return message.getBody();
        } else {
            User user = JSON.parseObject(content, User.class);
            return user;
        }
    }
}
