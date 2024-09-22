package xyz.toway.sales.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.toway.shared.other.RabbitMQConstants;

@Configuration
public class RabbitMQConfig {

    // Queue for adding
    @Bean
    public Queue addQueue() {
        return new Queue(RabbitMQConstants.ADD_QUEUE, false);
    }

    // Queue for deleting
    @Bean
    public Queue deleteQueue() {
        return new Queue(RabbitMQConstants.DELETE_QUEUE, false);
    }

    // Exchange
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(RabbitMQConstants.EXCHANGE_NAME);
    }

    // Binding for add queue and exchange
    @Bean
    public Binding addBinding(Queue addQueue, DirectExchange exchange) {
        return BindingBuilder.bind(addQueue).to(exchange).with(RabbitMQConstants.ADD_ROUTING_KEY);
    }

    // Binding for delete queue and exchange
    @Bean
    public Binding deleteBinding(Queue deleteQueue, DirectExchange exchange) {
        return BindingBuilder.bind(deleteQueue).to(exchange).with(RabbitMQConstants.DELETE_ROUTING_KEY);
    }

    // Configuring JSON converter
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
