package com.app.crypto.wallet.service;

import com.app.crypto.wallet.domain.Role;
import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.exceptions.UserNotFoundException;
import com.app.crypto.wallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    public User editUserAccount(User user) throws UserNotFoundException {
        Optional<User> findUserId = userRepository.findByUserId(user.getUserId());
        if (findUserId.isPresent()) {
            if (user.getUsername() != null) {
                user.setUsername(user.getUsername());
            }
            if (user.getPassword() != null) {
                user.setPassword(user.getPassword());
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

    public void deleteUserAccount() {
        long userId = 1L;
        userRepository.deleteById(userId);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) throws UserNotFoundException {
        return userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
    }
    public User createNewUser(User user) {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("USER"));
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        user.setMailAddressee(user.getMailAddressee());
        user.setRoles(roles);
        user.setEnabled(false);
        userRepository.save(user);
        return user;
    }
}
