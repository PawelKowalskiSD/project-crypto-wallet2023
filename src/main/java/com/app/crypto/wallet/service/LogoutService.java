package com.app.crypto.wallet.service;

import com.app.crypto.wallet.domain.Jwt;
import com.app.crypto.wallet.repository.JwtRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogoutService implements LogoutHandler {
    private final JwtRepository jwtRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String auth = request.getHeader("Authorization");
        String token;
        if (auth == null || !auth.startsWith("Bearer ")) {
            return;
        }
        token = auth.substring(7);
        Jwt soredToken = jwtRepository.findByToken(token).orElse(null);
        if (soredToken != null ) {
            soredToken.setExpired(true);
            jwtRepository.save(soredToken);
            SecurityContextHolder.clearContext();
        }
    }
}
