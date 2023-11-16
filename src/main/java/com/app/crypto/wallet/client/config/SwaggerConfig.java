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
                description = "There are two users in the database, the first is admin and the second is user, go to the auth-controller in the /auth/log-in endpoint and enter the data__USERNAME:admin__PASSWORD:admin123___USERNAME:user__PASSWORD:user123__" +
                        "After logging in, we receive a token in response, which should be copied and pasted by clicking the green button in the upper right corner of the screen with the word AUTHORIZE" +
                        "thanks to this we will gain access to other endpoints.__CREATE USER AND LOG-IN:__Creating a new user: go to auth-controller under endpoint /auth/sign-up, enter any username, password and email, after correct registration, you will receive a token in response," +
                        " which should be pasted under endpoint /auth/verify, this is to activate the account. then you can go to the /auth/log-in endpoint, enter the data and in response you will get a JWT which you can paste by clicking the green button on the right at the top of the screen with the word AUTHORIZE," +
                        " paste your token there and you can use it in other endpoints",
                title = "Crypto Wallet",
                version = "v1.0.3.8"
        ),
        servers = {
                @Server(
                        description = "Localhost",
                        url = "http://localhost:8080"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "paste JWT token",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
}
