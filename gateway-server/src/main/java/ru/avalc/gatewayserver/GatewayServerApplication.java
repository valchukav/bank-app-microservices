package ru.avalc.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.factory.TokenRelayGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServerApplication {

    private final TokenRelayGatewayFilterFactory filterFactory;

    public GatewayServerApplication(TokenRelayGatewayFilterFactory filterFactory) {
        this.filterFactory = filterFactory;
    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p ->
                        p.path("/accounts-service")
                                .filters(f -> f
                                        .filters(filterFactory.apply())
                                        .removeRequestHeader("Cookie"))
                                .uri("lb://accounts-service"))
                .route(p ->
                        p.path("/loans-service")
                                .filters(f -> f
                                        .filters(filterFactory.apply())
                                        .removeRequestHeader("Cookie"))
                                .uri("lb://loans-service"))
                .route(p ->
                        p.path("/cards-service")
                                .filters(f -> f
                                        .filters(filterFactory.apply())
                                        .removeRequestHeader("Cookie"))
                                .uri("lb://cards-service"))
                .build();
    }

}
