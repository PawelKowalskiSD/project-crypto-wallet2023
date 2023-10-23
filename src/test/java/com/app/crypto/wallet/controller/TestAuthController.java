package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.domain.JwtToken;
import com.app.crypto.wallet.domain.Role;
import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.domain.dto.AuthRequestDto;
import com.app.crypto.wallet.domain.dto.AuthResponseDto;
import com.app.crypto.wallet.domain.dto.CreateUserDto;
import com.app.crypto.wallet.mapper.DtoMapper;
import com.app.crypto.wallet.service.AuthService;
import com.app.crypto.wallet.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TestAuthController {
    private final AuthService authService = mock(AuthService.class);
    private final UserService userService = mock(UserService.class);
    private final DtoMapper dto = mock(DtoMapper.class);

    @Test
    void shouldLogin() {
        //Given
        AuthController authController = new AuthController(authService, userService, dto);
        AuthRequestDto requestDto = new AuthRequestDto("pablo", "pablo123");
        User userInDatabase = new User(1L,"pablo", "pablo123");
        when(dto.mapToUser(requestDto)).thenReturn(userInDatabase);
        JwtToken createJwtToken = new JwtToken(1L,"dafGHhGfFGSFSDFASDGsgGFfddasdd", false, userInDatabase);
        when(authService.getToken(userInDatabase)).thenReturn(createJwtToken);
        AuthResponseDto responseJwtToken = new AuthResponseDto("dafGHhGfFGSFSDFASDGsgGFfddasdd");
        when(dto.mapToAuthResponseDto(createJwtToken)).thenReturn(responseJwtToken);
        //When
        AuthResponseDto result = authController.login(requestDto).getBody();
        //Then
        assertEquals("dafGHhGfFGSFSDFASDGsgGFfddasdd", result.getAccessToken());
        verify(authService, times(1)).getToken(userInDatabase);
    }

    @Test
    void shouldSingUp() {
        //Given
        AuthController authController = new AuthController(authService, userService, dto);
        CreateUserDto requestCreateUserDto = new CreateUserDto("pablo", "pablo123", "mail@ap.pl", List.of("USER"));
        User createNewUser = new User(1L, "pablo", "pablo123", "mail@ap.pl", false, new ArrayList<>());
        when(dto.mapToUser(requestCreateUserDto)).thenReturn(createNewUser);
        User userInDatabase = new User(1L, "pablo", "pablo123", "mail@ap.pl", false, new ArrayList<>());
        when(userService.createNewUser(createNewUser)).thenReturn(userInDatabase);
        CreateUserDto responseCreateUserDto = new CreateUserDto("pablo", "pablo123", "mail@ap.pl", List.of("USER"));
        when(dto.mapToCreateUserDto(userInDatabase)).thenReturn(responseCreateUserDto);
        //When
        CreateUserDto result = authController.singUp(requestCreateUserDto).getBody();
        //Then
        assertEquals("pablo", result.getUsername());
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
