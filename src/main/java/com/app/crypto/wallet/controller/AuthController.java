package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.domain.dto.AuthRequestDto;
import com.app.crypto.wallet.domain.dto.AuthResponseDto;
import com.app.crypto.wallet.domain.dto.CreateUserDto;
import com.app.crypto.wallet.mapper.DtoMapper;
import com.app.crypto.wallet.service.AuthService;
import com.app.crypto.wallet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final DtoMapper dtoMapper;

    @PostMapping(value = "/log-in", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {
        return ResponseEntity.ok().body(dtoMapper.mapToAuthResponseDto(authService.getToken(dtoMapper.mapToUser(authRequestDto))));
    }

    @PostMapping(value = "/sing-up", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateUserDto> singUp(@RequestBody CreateUserDto createUserDto) {
        return ResponseEntity.ok().body(dtoMapper.mapToCreateUserDto(userService.createNewUser(dtoMapper.mapToUser(createUserDto))));
    }

    @GetMapping(value = "/verify")
    public ResponseEntity<Void> verifyAccount(@RequestParam String verifyKey) {
        authService.verify(verifyKey);
        return ResponseEntity.ok().build();
    }
}
