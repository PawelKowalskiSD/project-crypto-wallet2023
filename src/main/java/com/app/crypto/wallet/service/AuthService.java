package com.app.crypto.wallet.service;

import com.app.crypto.wallet.domain.Jwt;
import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.domain.VerificationKey;
import com.app.crypto.wallet.exceptions.UserNotFoundException;
import com.app.crypto.wallet.repository.JwtRepository;
import com.app.crypto.wallet.repository.UserRepository;
import com.app.crypto.wallet.repository.VerificationKeyRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final JwtRepository jwtRepository;
    private final VerificationKeyRepository verificationKeyRepository;
    private final UserRepository userRepository;


    public Jwt createJwt(User user) throws UserNotFoundException {
        User userInDatabase = userRepository.findByUsername(user.getUsername()).orElseThrow(UserNotFoundException::new);
        Algorithm algorithm = Algorithm.HMAC512("secret");
        String token = JWT.create()
                .withClaim("id", userInDatabase.getUserId())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withClaim("roles", userInDatabase.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        revokeTokens(userInDatabase);
        Jwt jwt = new Jwt(token, false, userInDatabase);
        jwtRepository.save(jwt);
        return jwt;
    }

    public void verify(String verifyToken) {
        VerificationKey verificationKey = verificationKeyRepository.findByValue(verifyToken);
        if (verificationKey != null) {
            User user = verificationKey.getUser();
            user.setEnabled(true);
            userRepository.save(user);
        }
    }

    private void revokeTokens(User user) {
        var validUserToken = jwtRepository.findAllJwtTokenByUser(user.getUserId());
        if (validUserToken.isEmpty())
            return;
        validUserToken.forEach(token -> {
            token.setExpired(true);
        });
    }

}
