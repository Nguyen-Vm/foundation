package org.linker.foundation.provider.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author RWM
 * @date 2018/6/21
 */
public final class RabbitConnection {

    private RabbitConnection() {}

    /**
     * 创建新的broker连接
     * @param mqUri "AMQP://${username}:${password}@${host}:${port}/${virtual host}";
     * @return
     */
    public static synchronized Connection create(String mqUri) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri(mqUri);
            return factory.newConnection();
        } catch (Exception e) {
            throw new RuntimeException(mqUri + ", create rabbitmq connection error: ", e);
        }
    }
}
