package org.linker.foundation.provider.rabbitmq;

import org.linker.foundation.JavaBean.RabbitMessage;
import org.linker.foundation.JavaBean.RabbitTopic;
import org.linker.foundation.dto.DateFormat;
import org.linker.foundation.utils.DateUtils;

import java.io.IOException;
import java.util.Date;

/**
 * @Author nguyenruan@ostay.cc
 * @Date 2019/3/7 19:52
 **/
public class Produce {
    private static final String MQ_URI = "AMQP://admin:admin@localhost:5672/rabbitmq";


    public static void main(String[] args) throws IOException, InterruptedException {
        RabbitOperations rabbitOperations = new RabbitOperations(MQ_URI);

        while (true) {
            RabbitMessage request = new RabbitMessage();
            request.name = "阮威敏";
            request.age = 23;
            request.phone = "17301747367";
            request.createTime = DateUtils.format(new Date(), DateFormat.StrikeDateTime);
            rabbitOperations.producer(RabbitTopic.DeveloperAction, request);
            Thread.sleep(1000);
        }

    }
}
