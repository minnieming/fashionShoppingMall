package com.fashionmall.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutingConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("CART-SERVICE", r -> r.path("/api/cart/**")
                        .uri("lb://module-cart-service"))
                .route("COUPON-SERVICE", r -> r.path("/api/coupon/**")
                        .uri("lb://module-coupon-service"))
                .route("IMAGE-SERVICE", r -> r.path("/api/image/**")
                        .uri("lb://module-image-service"))
                .route("ITEM-SERVICE", r -> r.path("/api/item/**")
                        .uri("lb://module-item-service"))
                .route("ORDER-SERVICE", r -> r.path("/api/order/**")
                        .uri("lb://module-order-service"))
                .route("REVIEW-SERVICE", r -> r.path("/api/review/**")
                        .uri("lb://module-review-service"))
                .route("USER-SERVICE", r -> r.path("/api/user/**")
                        .uri("lb://module-user-service"))
                .build();
    }

}
