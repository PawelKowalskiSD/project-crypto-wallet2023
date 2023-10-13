package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.domain.dto.AuthRequestDto;
import com.app.crypto.wallet.domain.dto.AuthResponseDto;
import com.app.crypto.wallet.domain.dto.CreateUserDto;
import com.app.crypto.wallet.mapper.DtoMapper;
import com.app.crypto.wallet.service.AuthService;
import com.app.crypto.wallet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final DtoMapper dtoMapper;

    @PostMapping(value = "/log-in", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AuthResponseDto login(@RequestBody AuthRequestDto authRequestDto) {
        return dtoMapper.mapJwtTokenToAuthResponseDto(authService.getToken(dtoMapper.mapAuthRequestDtoToUser(authRequestDto)));
    }

    @PostMapping(value = "/sing-up", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreateUserDto singUp(@RequestBody CreateUserDto createUserDto) {
        return dtoMapper.mapUserToCreateUserDto(userService.createNewUser(dtoMapper.mapCreateUserDtoToUser(createUserDto)));
    }

    @GetMapping(value = "/{verifyKey}")
    public void verifyAccount(@PathVariable String verifyKey) {
        authService.verify(verifyKey);
    }
}
