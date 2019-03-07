package org.linker.foundation.provider.rabbitmq;

import com.alibaba.fastjson.JSON;
import org.linker.foundation.JavaBean.RabbitMessage;
import org.linker.foundation.JavaBean.RabbitTopic;

import java.io.IOException;

/**
 * @Author nguyenruan@ostay.cc
 * @Date 2019/3/7 19:53
 **/
public class Consumer {

    private static final String MQ_URI = "AMQP://guest:guest@localhost:5672/rabbitmq";

    public static void main(String[] args) throws IOException {
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
    }
}
