package com.app.crypto.wallet.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailValidator {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]{4,}@[A-Za-z0-9.-]{2,}\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public boolean isValidEmail(String mail) {
        return EMAIL_PATTERN.matcher(mail).matches();
    }
}
