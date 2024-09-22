package xyz.toway.libraryservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.toway.shared.model.SharedSaleModel;
import xyz.toway.shared.other.RabbitMQConstants;

@Slf4j
@Component
public class MessageConsumer {

    private final StockService stockService;

    public MessageConsumer(@Autowired StockService stockService) {
        this.stockService = stockService;
    }

    @RabbitListener(queues = RabbitMQConstants.ADD_QUEUE)
    public void receiveAddMessage(SharedSaleModel message) {
        log.info("***** Received Add Message: {}", message);
        stockService.adjustStockItem(message);
    }

    @RabbitListener(queues = RabbitMQConstants.DELETE_QUEUE)
    public void receiveDeleteMessage(SharedSaleModel message) {
        log.info("***** Received Delete Message: {}", message);
        stockService.adjustStockItem(new SharedSaleModel(message.libraryId(), message.bookId(), -message.quantity()));
    }

}
