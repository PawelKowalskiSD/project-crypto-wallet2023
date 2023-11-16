package com.app.crypto.wallet.service;

import com.app.crypto.wallet.client.config.AuthConfig;
import com.app.crypto.wallet.domain.Mail;
import com.app.crypto.wallet.domain.Role;
import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.domain.VerificationKey;
import com.app.crypto.wallet.exceptions.*;
import com.app.crypto.wallet.repository.RoleRepository;
import com.app.crypto.wallet.repository.UserRepository;
import com.app.crypto.wallet.repository.VerificationKeyRepository;
import com.app.crypto.wallet.validator.EmailValidator;
import com.app.crypto.wallet.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
    private final EmailValidator emailValidator;
    private final UserValidator userValidator;

    public User editUserAccount(User user) throws UserNotFoundException, UserPermissionsException, DuplicateUsernameException, WrongEmailFormatException, DuplicateMailAddresseeException {
        User findUser = getUserById(user.getUserId());
        if (findUser != null) {
            if (user.getPassword() != null && !userValidator.checkMailInDatabase(user.getMailAddressee()) && !userValidator.validateUserName(user.getUsername())) {
                findUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            if (user.getMailAddressee() != null) {
                if (emailValidator.isValidEmail(user.getMailAddressee()) && !userValidator.checkMailInDatabase(user.getMailAddressee()) && !userValidator.validateUserName(user.getUsername())) {
                    findUser.setMailAddressee(user.getMailAddressee());
                } else if (!emailValidator.isValidEmail(user.getMailAddressee()) && !userValidator.checkMailInDatabase(user.getMailAddressee())) {
                    throw new WrongEmailFormatException();
                } else if (emailValidator.isValidEmail(user.getMailAddressee()) && userValidator.checkMailInDatabase(user.getMailAddressee())) {
                    throw new DuplicateMailAddresseeException();
                } else if (!emailValidator.isValidEmail(user.getMailAddressee()) && userValidator.checkMailInDatabase(user.getMailAddressee())) {
                    throw new WrongEmailFormatException();
                }
            }
            if (user.getUsername() != null) {
                if (!userValidator.validateUserName(user.getUsername()))
                    findUser.setUsername(user.getUsername());
                else
                    throw new DuplicateUsernameException();
            }
            userRepository.save(findUser);
        }
        return findUser;
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

    public User createNewUser(User user) throws RoleNotFoundException, IncompleteDataException, WrongEmailFormatException, DuplicateUsernameException, DuplicateMailAddresseeException {
        if (user.getUsername() != null && user.getMailAddressee() != null && user.getPassword() != null) {
            String verifyToken = UUID.randomUUID().toString();
            Role role = roleRepository.findByRoleName("USER").orElseThrow(RoleNotFoundException::new);
            if (!userValidator.validateUserName(user.getUsername()))
                user.setUsername(user.getUsername());
            else
                throw new DuplicateUsernameException();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if (emailValidator.isValidEmail(user.getMailAddressee()) && !userValidator.checkMailInDatabase(user.getMailAddressee()))
                user.setMailAddressee(user.getMailAddressee());
            else if (!emailValidator.isValidEmail(user.getMailAddressee()) && !userValidator.checkMailInDatabase(user.getMailAddressee()))
                throw new WrongEmailFormatException();
            else if (emailValidator.isValidEmail(user.getMailAddressee()) && userValidator.checkMailInDatabase(user.getMailAddressee()))
                throw new DuplicateMailAddresseeException();
            else
                throw new WrongEmailFormatException();
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
        } else
            throw new IncompleteDataException();
    }

    public User findByUserId(long userId) throws UserNotFoundException {
        return userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
    }
}
