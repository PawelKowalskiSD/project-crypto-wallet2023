package com.app.crypto.wallet.service;

import com.app.crypto.wallet.client.config.AuthConfig;
import com.app.crypto.wallet.domain.Mail;
import com.app.crypto.wallet.domain.Role;
import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.domain.VerificationKey;
import com.app.crypto.wallet.exceptions.RoleNotFoundException;
import com.app.crypto.wallet.exceptions.UserNotFoundException;
import com.app.crypto.wallet.exceptions.UserPermissionsException;
import com.app.crypto.wallet.repository.RoleRepository;
import com.app.crypto.wallet.repository.UserRepository;
import com.app.crypto.wallet.repository.VerificationKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final VerificationKeyRepository verificationKeyRepository;
    private final MailSenderService mailSenderService;
    private final AuthConfig authConfig;

    public User editUserAccount(User user) throws UserNotFoundException, UserPermissionsException {
        User findUserId = getUserById(user.getUserId());
        if (findUserId != null) {
            if (user.getUsername() != null) {
                findUserId.setUsername(user.getUsername());
            }
            if (user.getPassword() != null) {
                findUserId.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            if (user.getMailAddressee() != null) {
                findUserId.setMailAddressee(user.getMailAddressee());
            }
            userRepository.save(findUserId);
        }
        return findUserId;
    }

    public void deleteUserAccount(Long userId) throws UserPermissionsException, UserNotFoundException {
        User user = getUserById(userId);
        boolean roleAdminAssigned = user.getRoles().stream().map(Role::getRoleName).anyMatch(u -> u.contains("ADMIN"));
        if (user.getUserId() == userId || roleAdminAssigned)
            userRepository.deleteById(userId);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) throws UserNotFoundException, UserPermissionsException {
        long validateUserId = authConfig.getUserIdFromAuthentication();
        User user = userRepository.findByUserId(validateUserId).orElseThrow(UserNotFoundException::new);
        boolean roleAdminAssigned = user.getRoles().stream().map(Role::getRoleName).anyMatch(u -> u.contains("ADMIN"));
        if (userId == validateUserId || roleAdminAssigned)
            return userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
        else
            throw new UserPermissionsException();
    }

    public User createNewUser(User user) throws RoleNotFoundException {
        String verifyToken = UUID.randomUUID().toString();
        Role role = roleRepository.findByRoleName("USER").orElseThrow(RoleNotFoundException::new);
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setMailAddressee(user.getMailAddressee());
        user.setRoles(List.of(role));
        user.setEnabled(false);
        VerificationKey verificationKey = new VerificationKey(verifyToken, user);
        user.setVerificationKey(verificationKey);
        userRepository.save(user);
        verificationKeyRepository.save(verificationKey);

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

    public User findByUserId(long userId) throws UserNotFoundException {
        return userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
    }
}
