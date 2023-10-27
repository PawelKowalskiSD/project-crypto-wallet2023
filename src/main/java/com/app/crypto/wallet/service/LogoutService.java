package com.app.crypto.wallet.service;

import com.app.crypto.wallet.domain.Jwt;
import com.app.crypto.wallet.exceptions.JwtNotFoundException;
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
        if (auth == null || !auth.startsWith("Bearer ")) {
            return;
        }
        String token = auth.substring(7);
        try {
         Jwt soredToken = jwtRepository.findByToken(token).orElseThrow(JwtNotFoundException::new);
            if (soredToken != null ) {
                soredToken.setExpired(true);
                jwtRepository.save(soredToken);
                SecurityContextHolder.clearContext();
            }
        } catch (JwtNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
