package com.app.crypto.wallet.service;

import com.app.crypto.wallet.client.config.AuthConfig;
import com.app.crypto.wallet.domain.Role;
import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.exceptions.UserNotFoundException;
import com.app.crypto.wallet.exceptions.UserPermissionsException;
import com.app.crypto.wallet.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthConfig authConfig;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void ShouldEditUserAccount() throws UserPermissionsException, UserNotFoundException {
        //Given
        User modifyJan = new User(1L, "jan", "jan123", "jan@wp.pl", true, List.of(new Role(1L,"USER")));
        when(authConfig.getUserIdFromAuthentication()).thenReturn(modifyJan.getUserId());
        User modifiedJan = new User(1L, "jan", "jan123", "janek@wp.pl", true, List.of(new Role(1L,"USER")));
        when(userRepository.findByUserId(modifiedJan.getUserId())).thenReturn(Optional.of(modifyJan));
        when(passwordEncoder.encode(modifiedJan.getPassword())).thenReturn(modifyJan.getPassword());
        //When
        User result = userService.editUserAccount(modifyJan);
        //Then
        assertEquals("jan@wp.pl", result.getMailAddressee());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(2L));
        verify(userRepository, times(1)).findByUserId(modifyJan.getUserId());
        verify(passwordEncoder, times(1)).encode(modifyJan.getPassword());
    }

    @Test
    void shouldDeleteUserAccount() {
        //Given
        //When
        //Then
    }

    @Test
    void shouldGetAllUser() {
        //Given
        //When
        //Then
    }

    @Test
    void shouldGetUserById() {
        //Given
        //When
        //Then
    }

    @Test
    void shouldCreateNewUser() {
        //Given
        //When
        //Then
    }

    @Test
    void ShouldFindByUserId() {
        //Given
        //When
        //Then
    }
}