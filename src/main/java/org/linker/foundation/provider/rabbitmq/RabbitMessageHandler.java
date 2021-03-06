package org.linker.foundation.provider.rabbitmq;

public interface RabbitMessageHandler {
    default byte[] handle(String contentType, byte[] body) {
        return new byte[0];
    }
}