package com.app.crypto.wallet.service;

import com.app.crypto.wallet.domain.Jwt;
import com.app.crypto.wallet.domain.Role;
import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.domain.VerificationKey;
import com.app.crypto.wallet.exceptions.UserNotFoundException;
import com.app.crypto.wallet.repository.JwtRepository;
import com.app.crypto.wallet.repository.UserRepository;
import com.app.crypto.wallet.repository.VerificationKeyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private JwtRepository jwtRepository;
    @Mock
    private VerificationKeyRepository verificationKeyRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldCreateJwt() throws UserNotFoundException {
      //Given
        User databaseUser = new User(1L, "jan", "jan123", "jan@wp.pl", true, List.of(new Role(1L, "USER")));
        Jwt jwt = new Jwt(1L, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                "eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ." +
                "SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c", false, databaseUser);
        when(userRepository.findByUsername(databaseUser.getUsername())).thenReturn(Optional.of(databaseUser));
        when(jwtRepository.save(any(Jwt.class))).thenReturn(jwt);
      //When
        Jwt result = authService.createJwt(databaseUser);
      //Then
        assertNotNull(result);
        assertFalse(result.isExpired());
        assertEquals(databaseUser.getUserId(), result.getUser().getUserId());
        verify(userRepository, times(1)).findByUsername(databaseUser.getUsername());
        verify(jwtRepository, times(1)).save(any(Jwt.class));
    }

    @Test
    void shouldVerify() {
        //Given
        String verifyKey = "satgwrgfbgfwqe2sgddaweas";
        User jan = new User(1L, "jan", "jan123", "jan@wp.pl", false, List.of(new Role(1L, "USER")));
        VerificationKey databaseVerifyKey = new VerificationKey(1L,"satgwrgfbgfwqe2sgddaweas", jan);
        when(verificationKeyRepository.findByValue(verifyKey)).thenReturn(databaseVerifyKey);
        when(userRepository.save(any(User.class))).thenReturn(jan);
        //When
        authService.verify(verifyKey);
        //Then
        assertTrue(jan.isEnabled());
        verify(verificationKeyRepository, times(1)).findByValue(verifyKey);
        verify(userRepository, times(1)).save(jan);
    }


}