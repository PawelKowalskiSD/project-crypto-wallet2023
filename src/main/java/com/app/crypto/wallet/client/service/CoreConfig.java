package com.app.crypto.wallet.client.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CoreConfig {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
