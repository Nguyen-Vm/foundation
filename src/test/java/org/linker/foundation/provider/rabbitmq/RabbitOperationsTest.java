package org.linker.foundation.provider.rabbitmq;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.linker.foundation.JavaBean.RabbitMessage;
import org.linker.foundation.JavaBean.RabbitTopic;

import java.io.IOException;

/**
 * @author RWM
 * @date 2018/8/27
 */
public class RabbitOperationsTest {

    private static final String MQ_URI = "AMQP://rwming:rwming@localhost:5672/message";

    @Test
    public void main() throws IOException {
        RabbitOperations rabbitOperations = new RabbitOperations(MQ_URI);

        // 消费
        rabbitOperations.consumer(RabbitTopic.DeveloperAction, false, new RabbitMessageHandler() {
            @Override
            public byte[] handle(String contentType, byte[] body) {
                if ("RabbitMessage".equals(contentType)) {
                    RabbitMessage request = JSON.parseObject(body, RabbitMessage.class);
                    System.out.println(request);
                }
                return null;
            }
        });

        // 生产
        RabbitMessage request = new RabbitMessage();
        request.name = "阮威敏";
        request.age = 23;
        request.phone = "17301747367";
        rabbitOperations.producer(RabbitTopic.DeveloperAction, request);

    }
}
