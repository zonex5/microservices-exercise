package xyz.toway.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/authors/**").uri("lb://book-service/authors"))
                .route(p -> p.path("/books/**").uri("lb://book-service/books"))
                .route(p -> p.path("/libraries/**").uri("lb://library-service/libraries"))
                .route(p -> p.path("/stock/**").uri("lb://library-service/stock"))
                .route(p -> p.path("/sales/**").uri("lb://sales-service/sales"))
                .route(p -> p.path("/search/**").uri("lb://search-service/search"))
                .build();
    }
}
