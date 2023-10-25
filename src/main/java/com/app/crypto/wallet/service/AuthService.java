package com.app.crypto.wallet.service;

import com.app.crypto.wallet.domain.Jwt;
import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.domain.VerificationKey;
import com.app.crypto.wallet.repository.JwtRepository;
import com.app.crypto.wallet.repository.UserRepository;
import com.app.crypto.wallet.repository.VerificationKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class AuthService {
    private final JwtRepository jwtRepository;
    private final VerificationKeyRepository verificationKeyRepository;
    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;


    public Jwt getToken(User user) {
        return null;
    }

    public void verify(String verifyToken) {
        VerificationKey verificationKey = verificationKeyRepository.findByValue(verifyToken);
        if (verificationKey != null) {
            User user = verificationKey.getUser();
            user.setEnabled(true);
            userRepository.save(user);
        }
    }
}
