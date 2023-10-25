package com.app.crypto.wallet.service;

import com.app.crypto.wallet.domain.Mail;
import com.app.crypto.wallet.domain.Role;
import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.domain.VerificationKey;
import com.app.crypto.wallet.exceptions.UserNotFoundException;
import com.app.crypto.wallet.repository.RoleRepository;
import com.app.crypto.wallet.repository.UserRepository;
import com.app.crypto.wallet.repository.VerificationKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;

    public User editUserAccount(User user) throws UserNotFoundException {
        Optional<User> findUserId = userRepository.findByUserId(user.getUserId());
        if (findUserId.isPresent()) {
            if (user.getUsername() != null) {
                user.setUsername(user.getUsername());
            }
            if (user.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            if (user.getMailAddressee() != null) {
                user.setMailAddressee(user.getMailAddressee());
            }
            userRepository.save(user);
        } else {
            throw new UserNotFoundException();
        }
        return user;
    }

    public void deleteUserAccount(Long userId) {
        userId = 1L;
        userRepository.deleteById(userId);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) throws UserNotFoundException {
        return userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
    }
    public User createNewUser(User user) {
        String verifyToken = UUID.randomUUID().toString();
        VerificationKey verificationKey = new VerificationKey(verifyToken, user);

        List<Role> roles = roleRepository.findRoleByRoleName("USER");
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setMailAddressee(user.getMailAddressee());
        user.setRoles(roles);
        user.setEnabled(false);
        user.setVerificationKey(verificationKey);
        userRepository.save(user);
        mailSenderService.sendMailToActiveAccount(Mail.builder()
                .mailTo(user.getMailAddressee())
                .subject("Verify Key")
                .message("We send you mail")
                .toCc(null)
                .build(),
                verificationKey
        );
        return user;
    }
}
