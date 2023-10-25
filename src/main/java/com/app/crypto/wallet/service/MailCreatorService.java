package com.app.crypto.wallet.service;

import com.app.crypto.wallet.client.config.AppConfig;
import com.app.crypto.wallet.domain.VerificationKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RequiredArgsConstructor
@Service
public class MailCreatorService {
    private final TemplateEngine templateEngine;
    private final AppConfig appConfig;

    public String BuildVerifyEmail(String message, VerificationKey verificationKey) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("appConfig", appConfig);
        context.setVariable("url", "auth/verify?verification=");
        context.setVariable("verification", verificationKey.getValue());
        return templateEngine.process("email.html", context);
    }

}
