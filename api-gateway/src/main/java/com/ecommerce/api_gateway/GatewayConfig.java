package com.ecommerce.api_gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("inventory_route", r -> r.path("/inventory/**")
                        .uri("lb://INVENTORY-SERVICE"))
                .route("shop_route", r -> r.path("/shop/**")
                        .uri("lb://SHOP-SERVICE"))
                .build();
    }
}
