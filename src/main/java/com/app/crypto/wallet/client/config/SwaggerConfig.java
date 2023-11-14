package com.app.crypto.wallet.client.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Pawel",
                        email = "kowalski.pawel.sd@gmail.com"
                ),
                description = "",
                title = "Crypto Wallet",
                version = "v1.0.3.6"
        ),
        servers = {
                @Server(
                        description = "",
                        url = ""
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
}
