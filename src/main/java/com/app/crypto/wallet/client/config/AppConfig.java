package com.app.crypto.wallet.client.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AppConfig {
    @Value("${coinGecko.api.endpoint.prod}")
    private String coinGeckoBasicUrl;

    @Value("${localhost.api.endpoint.prod}")
    private String basicUrl;

    @Value("${application.security.jwt.secret-key}")
    private String secret;
}
