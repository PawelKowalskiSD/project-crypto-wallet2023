package com.app.crypto.wallet.service;

import com.app.crypto.wallet.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    public User editUserAccount(User user) {
        return user;
    }

    public void deleteUserAccount() {
    }

    public List<User> getAllUser() {
        return null;
    }

    public User getUserById(Long userId) {
        return null;
    }
    public User createNewUser(User user) {
        return user;
    }
}
