package com.app.crypto.wallet.client.config;

import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.exceptions.UserPermissionsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthConfig {

    public Long getUserIdFromAuthentication() throws UserPermissionsException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserPermissionsException();
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof User) {
            return ((User) principal).getUserId();
        } else {
            throw new UserPermissionsException();
        }
    }
}
