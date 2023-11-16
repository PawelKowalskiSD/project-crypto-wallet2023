package com.app.crypto.wallet.validator;

import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserValidator {
    private final UserRepository userRepository;

    public boolean validateUserName(String username) {
        Optional<User> findUsername = userRepository.findByUsername(username);
        return findUsername.isPresent();
    }

    public boolean checkMailInDatabase(String mailAddressee) {
        Optional<User> findMailAddressee = userRepository.findByMailAddressee(mailAddressee);
        return findMailAddressee.isPresent();
    }
}
