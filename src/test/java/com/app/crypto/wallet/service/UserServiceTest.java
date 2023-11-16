package com.app.crypto.wallet.service;

import com.app.crypto.wallet.client.config.AuthConfig;
import com.app.crypto.wallet.domain.*;
import com.app.crypto.wallet.exceptions.*;
import com.app.crypto.wallet.repository.RoleRepository;
import com.app.crypto.wallet.repository.UserRepository;
import com.app.crypto.wallet.repository.VerificationKeyRepository;
import com.app.crypto.wallet.validator.EmailValidator;
import com.app.crypto.wallet.validator.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private VerificationKeyRepository verificationKeyRepository;
    @Mock
    private MailSenderService mailSenderService;
    @Mock
    private EmailValidator emailValidator;
    @Mock
    private UserValidator userValidator;
    @InjectMocks
    private UserService userService;

    @Test
    void ShouldEditUserAccount() throws UserPermissionsException, UserNotFoundException, WrongEmailFormatException, DuplicateUsernameException, DuplicateMailAddresseeException {
        //Given
        User modifyJan = new User(1L, "jan", "jan123", "jan11@wp.pl", true, List.of(new Role(1L, "USER")));
        when(authConfig.getUserIdFromAuthentication()).thenReturn(modifyJan.getUserId());
        User modifiedJan = new User(1L, "jan1", "jan123", "janek@wp.pl", true, List.of(new Role(1L, "USER")));
        when(userRepository.findByUserId(modifyJan.getUserId())).thenReturn(Optional.of(modifyJan));
        when(userValidator.validateUserName(modifyJan.getUsername())).thenReturn(false);
        when(userValidator.checkMailInDatabase(modifyJan.getMailAddressee())).thenReturn(false);
        when(emailValidator.isValidEmail(modifyJan.getMailAddressee())).thenReturn(true);
        when(passwordEncoder.encode(modifiedJan.getPassword())).thenReturn(modifyJan.getPassword());
        //When
        User result = userService.editUserAccount(modifyJan);
        //Then
        assertEquals(1L, result.getUserId());
        assertEquals("jan", result.getUsername());
        assertEquals("jan11@wp.pl", result.getMailAddressee());
        assertThrows(UserPermissionsException.class, () -> userService.getUserById(2L));
        verify(userRepository, times(3)).findByUserId(modifyJan.getUserId());
        verify(passwordEncoder, times(1)).encode(modifyJan.getPassword());
    }

    @Test
    void shouldDeleteUserAccount() throws UserPermissionsException, UserNotFoundException {
        //Given
        User databaseUser = new User(1L, "jan", "jan123", "jan@wp.pl", true, List.of(new Role(1L, "USER")));
        long requestUserId = 1L;
        when(authConfig.getUserIdFromAuthentication()).thenReturn(databaseUser.getUserId());
        when(userRepository.findByUserId(requestUserId)).thenReturn(Optional.of(databaseUser));
        //When
        userService.deleteUserAccount(requestUserId);
        //Then
        assertThrows(UserPermissionsException.class, () -> userService.deleteUserAccount(2L));
        verify(userRepository, times(1)).deleteById(requestUserId);
    }

    @Test
    void shouldGetAllUser() {
        //Given
        List<User> databaseUsers = new ArrayList<>();
        Role admin = new Role(1L, "ADMIN");
        Role user = new Role(2L, "USER");
        Wallet newWallet = new Wallet(1L, "new wallet");
        Wallet secondWallet = new Wallet(2L, "second wallet");
        databaseUsers.add(new User(1L, "jan", "mail@123", true, List.of(admin), List.of(newWallet)));
        databaseUsers.add(new User(2L, "tom", "mail@2p", false, List.of(user, admin), List.of(secondWallet)));
        when(userRepository.findAll()).thenReturn(databaseUsers);
        //When
        List<User> result = userService.getAllUser();
        //Then
        assertEquals(1L, result.get(0).getUserId());
        assertEquals("jan", result.get(0).getUsername());
        assertEquals("mail@123", result.get(0).getMailAddressee());
        assertEquals(2L, result.get(1).getUserId());
        assertEquals("tom", result.get(1).getUsername());
        assertEquals("mail@2p", result.get(1).getMailAddressee());
        assertTrue(result.get(0).isEnabled());
        assertFalse(result.get(1).isEnabled());
        assertEquals(1L, result.get(0).getRoles().get(0).getRoleId());
        assertEquals("ADMIN", result.get(0).getRoles().get(0).getRoleName());
        assertEquals(2L, result.get(1).getRoles().get(0).getRoleId());
        assertEquals(1L, result.get(1).getRoles().get(1).getRoleId());
        assertEquals("USER", result.get(1).getRoles().get(0).getRoleName());
        assertEquals("ADMIN", result.get(1).getRoles().get(1).getRoleName());
        assertEquals("new wallet", result.get(0).getWalletList().get(0).getWalletName());
        assertEquals("second wallet", result.get(1).getWalletList().get(0).getWalletName());
        assertEquals(1L, result.get(0).getWalletList().get(0).getWalletId());
        assertEquals(2L, result.get(1).getWalletList().get(0).getWalletId());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void shouldGetUserById() throws UserPermissionsException, UserNotFoundException {
        //Given
        Role userRole = new Role(1L, "USER");
        Wallet newWallet = new Wallet(1L, "new wallet");
        User jan = new User(1L, "jan", "mail@123", true, List.of(userRole), List.of(newWallet));
        when(authConfig.getUserIdFromAuthentication()).thenReturn(jan.getUserId());
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(jan));
        //When
        User result = userService.getUserById(jan.getUserId());
        //Then
        assertEquals(1L, result.getUserId());
        assertEquals("jan", result.getUsername());
        assertEquals("mail@123", result.getMailAddressee());
        assertTrue(result.isEnabled());
        assertEquals(1L, result.getWalletList().get(0).getWalletId());
        assertEquals("new wallet", result.getWalletList().get(0).getWalletName());
        assertEquals(1L, result.getRoles().get(0).getRoleId());
        assertEquals("USER", result.getRoles().get(0).getRoleName());
        verify(userRepository, times(2)).findByUserId(jan.getUserId());
    }

    @Test
    void shouldCreateNewUser() throws RoleNotFoundException, IncompleteDataException, WrongEmailFormatException, DuplicateUsernameException, DuplicateMailAddresseeException {
        //Given
        String verifyToken = UUID.randomUUID().toString();
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(1L, "USER"));
        User jan = new User(1L, "jan", "jan123", "mail@123", false, roles);
        VerificationKey verificationKey = new VerificationKey();
        verificationKey.setValue(verifyToken);
        verificationKey.setUser(jan);
        when(roleRepository.findByRoleName(roles.get(0).getRoleName())).thenReturn(Optional.of(jan.getRoles().get(0)));
        when(userRepository.save(any(User.class))).thenReturn(jan);
        when(emailValidator.isValidEmail(jan.getMailAddressee())).thenReturn(true);
        when(userValidator.validateUserName(jan.getUsername())).thenReturn(false);
        when(userValidator.checkMailInDatabase(jan.getMailAddressee())).thenReturn(false);
        when(verificationKeyRepository.save(any(VerificationKey.class))).thenReturn(verificationKey);
        doNothing().when(mailSenderService).sendMailToActiveAccount(any(Mail.class), any(VerificationKey.class));
        //When
        User result = userService.createNewUser(jan);
        //Then
        assertEquals(1L, result.getUserId());
        assertEquals("jan", result.getUsername());
        assertEquals("mail@123", result.getMailAddressee());
        assertFalse(result.isEnabled());
        assertEquals(1L, result.getRoles().get(0).getRoleId());
        assertEquals("USER", result.getRoles().get(0).getRoleName());
        verify(mailSenderService).sendMailToActiveAccount(any(Mail.class), any(VerificationKey.class));
        verify(roleRepository, times(1)).findByRoleName(roles.get(0).getRoleName());
        verify(userRepository, times(1)).save(jan);
        verify(emailValidator, times(1)).isValidEmail(jan.getMailAddressee());
        verify(userValidator, times(1)).validateUserName(jan.getUsername());
        verify(userValidator, times(1)).checkMailInDatabase(jan.getMailAddressee());
    }

    @Test
    void ShouldFindByUserId() throws UserNotFoundException {
        //Given
        Role userRole = new Role(1L, "USER");
        Wallet newWallet = new Wallet(1L, "new wallet");
        User jan = new User(1L, "jan", "mail@123", true, List.of(userRole), List.of(newWallet));
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(jan));
        //When
        User result = userService.findByUserId(jan.getUserId());
        //Then
        assertEquals(1L, result.getUserId());
        assertEquals("jan", result.getUsername());
        assertEquals("mail@123", result.getMailAddressee());
        assertTrue(result.isEnabled());
        assertEquals(1L, result.getWalletList().get(0).getWalletId());
        assertEquals("new wallet", result.getWalletList().get(0).getWalletName());
        assertEquals(1L, result.getRoles().get(0).getRoleId());
        assertEquals("USER", result.getRoles().get(0).getRoleName());
        verify(userRepository, times(1)).findByUserId(jan.getUserId());
    }
}