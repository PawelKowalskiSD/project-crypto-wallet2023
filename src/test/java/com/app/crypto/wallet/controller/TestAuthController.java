package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.domain.Jwt;
import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.domain.VerificationKey;
import com.app.crypto.wallet.domain.dto.AuthRequestDto;
import com.app.crypto.wallet.domain.dto.AuthResponseDto;
import com.app.crypto.wallet.domain.dto.CreateUserDto;
import com.app.crypto.wallet.exceptions.*;
import com.app.crypto.wallet.mapper.DtoMapper;
import com.app.crypto.wallet.service.AuthService;
import com.app.crypto.wallet.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TestAuthController {
    private final AuthService authService = mock(AuthService.class);
    private final UserService userService = mock(UserService.class);
    private final DtoMapper dto = mock(DtoMapper.class);

    @Test
    void shouldLogin() throws UserNotFoundException, UserAccountVerificationException {
        //Given
        AuthController authController = new AuthController(authService, userService, dto);
        AuthRequestDto requestDto = new AuthRequestDto("pablo", "pablo123");
        User userInDatabase = new User(1L, "pablo", "pablo123");
        when(dto.mapToUser(requestDto)).thenReturn(userInDatabase);
        Jwt createJwt = new Jwt(1L, "dafGHhGfFGSFSDFASDGsgGFfddasdd", false, userInDatabase);
        when(authService.createJwt(userInDatabase)).thenReturn(createJwt);
        AuthResponseDto responseJwtToken = new AuthResponseDto("dafGHhGfFGSFSDFASDGsgGFfddasdd");
        when(dto.mapToAuthResponseDto(createJwt)).thenReturn(responseJwtToken);
        //When
        AuthResponseDto result = authController.login(requestDto).getBody();
        //Then
        assertEquals("dafGHhGfFGSFSDFASDGsgGFfddasdd", result.getAccessToken());
        verify(authService, times(1)).createJwt(userInDatabase);
    }

    @Test
    void shouldSingUp() throws RoleNotFoundException, IncompleteDataException, WrongEmailFormatException, DuplicateUsernameException, DuplicateMailAddresseeException {
        //Given
        AuthController authController = new AuthController(authService, userService, dto);
        CreateUserDto requestCreateUserDto = new CreateUserDto("pablo", "pablo123", "mail@ap.pl");
        User createNewUser = new User(1L, "pablo", "pablo123", "mail@ap.pl", false, new ArrayList<>());
        when(dto.mapToUser(requestCreateUserDto)).thenReturn(createNewUser);
        User userInDatabase = new User(1L, "pablo", "pablo123", "mail@ap.pl", false, new ArrayList<>());
        VerificationKey verificationKey = new VerificationKey("dafGHhGfFGSFSDFASDGsgGFfddasdd", userInDatabase);
        when(userService.createNewUser(createNewUser)).thenReturn(userInDatabase);
        AuthResponseDto responseCreateUserDto = new AuthResponseDto(verificationKey.getValue());
        when(dto.mapToAuthResponseDto(userInDatabase)).thenReturn(responseCreateUserDto);
        //When
        AuthResponseDto result = authController.singUp(requestCreateUserDto).getBody();
        //Then
        assertEquals("dafGHhGfFGSFSDFASDGsgGFfddasdd", result.getAccessToken());
        verify(userService, times(1)).createNewUser(createNewUser);
    }

    @Test
    void shouldVerifyAccount() {
        //Given
        AuthController authController = new AuthController(authService, userService, dto);
        String requestVerifyKey = "sdgsgkanjoogajgiojdo";
        //When
        authController.verifyAccount(requestVerifyKey);
        //Then
        verify(authService, times(1)).verify(requestVerifyKey);
    }
}
