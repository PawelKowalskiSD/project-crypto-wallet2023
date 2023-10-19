package com.app.crypto.wallet.service;

import com.app.crypto.wallet.domain.JwtToken;
import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.domain.VerificationKey;
import com.app.crypto.wallet.repository.JwtTokenRepository;
import com.app.crypto.wallet.repository.UserRepository;
import com.app.crypto.wallet.repository.VerificationKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class AuthService {
    private final JwtTokenRepository jwtTokenRepository;
    private final VerificationKeyRepository verificationKeyRepository;
    private final UserRepository userRepository;


    public JwtToken getToken(User user) {
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
