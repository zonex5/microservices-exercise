package xyz.toway.libraryservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import xyz.toway.libraryservice.config.RabbitMQConfig;
import xyz.toway.shared.model.SharedSaleModel;

@Component
public class MessageConsumer {

    @RabbitListener(queues = RabbitMQConfig.ADD_QUEUE)
    public void receiveAddMessage(SharedSaleModel message) {

    }

    @RabbitListener(queues = RabbitMQConfig.DELETE_QUEUE)
    public void receiveDeleteMessage(SharedSaleModel message) {

    }

}
