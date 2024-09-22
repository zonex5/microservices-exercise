package xyz.toway.sales.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Queue for adding records
    public static final String ADD_QUEUE = "addQueue";

    // Queue for deleting records
    public static final String DELETE_QUEUE = "deleteQueue";

    // Exchange for both queues
    public static final String EXCHANGE_NAME = "recordExchange";

    // Routing keys
    public static final String ADD_ROUTING_KEY = "addRoutingKey";
    public static final String DELETE_ROUTING_KEY = "deleteRoutingKey";

    // Queue for adding
    @Bean
    public Queue addQueue() {
        return new Queue(ADD_QUEUE, false);
    }

    // Queue for deleting
    @Bean
    public Queue deleteQueue() {
        return new Queue(DELETE_QUEUE, false);
    }

    // Exchange
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    // Binding for add queue and exchange
    @Bean
    public Binding addBinding(Queue addQueue, DirectExchange exchange) {
        return BindingBuilder.bind(addQueue).to(exchange).with(ADD_ROUTING_KEY);
    }

    // Binding for delete queue and exchange
    @Bean
    public Binding deleteBinding(Queue deleteQueue, DirectExchange exchange) {
        return BindingBuilder.bind(deleteQueue).to(exchange).with(DELETE_ROUTING_KEY);
    }

    // Configuring JSON converter
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
