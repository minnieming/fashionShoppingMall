package com.fashionmall.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@PropertySource(
    value = {"classpath:application-common-${spring.profiles.active}.yml"},
    factory = YmlPropertySourceFactory.class)
@RequiredArgsConstructor
public class AppConfig {

    private final EntityManager em;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
