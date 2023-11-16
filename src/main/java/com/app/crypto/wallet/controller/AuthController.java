package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.domain.dto.AuthRequestDto;
import com.app.crypto.wallet.domain.dto.AuthResponseDto;
import com.app.crypto.wallet.domain.dto.CreateUserDto;
import com.app.crypto.wallet.exceptions.*;
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
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto) throws UserNotFoundException, UserAccountVerificationException {
        return ResponseEntity.ok().body(dtoMapper.mapToAuthResponseDto(authService.createJwt(dtoMapper.mapToUser(authRequestDto))));
    }

    @PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponseDto> singUp(@RequestBody CreateUserDto createUserDto) throws RoleNotFoundException, IncompleteDataException, WrongEmailFormatException, DuplicateUsernameException, DuplicateMailAddresseeException {
        return ResponseEntity.ok().body(dtoMapper.mapToAuthResponseDto(userService.createNewUser(dtoMapper.mapToUser(createUserDto))));
    }

    @GetMapping(value = "/verify")
    public ResponseEntity<Void> verifyAccount(@RequestParam String verification) {
        authService.verify(verification);
        return ResponseEntity.ok().build();
    }
}
