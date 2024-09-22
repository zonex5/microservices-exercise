package xyz.toway.shared.other;

public class RabbitMQConstants {

    // Queue for adding records
    public static final String ADD_QUEUE = "addQueue";

    // Queue for deleting records
    public static final String DELETE_QUEUE = "deleteQueue";

    // Exchange for both queues
    public static final String EXCHANGE_NAME = "recordExchange";

    // Routing keys
    public static final String ADD_ROUTING_KEY = "addRoutingKey";
    public static final String DELETE_ROUTING_KEY = "deleteRoutingKey";
}
