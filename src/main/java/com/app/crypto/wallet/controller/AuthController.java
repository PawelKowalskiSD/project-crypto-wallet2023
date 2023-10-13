package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.domain.dto.AuthRequestDto;
import com.app.crypto.wallet.domain.dto.AuthResponseDto;
import com.app.crypto.wallet.mapper.DtoMapper;
import com.app.crypto.wallet.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;
    private final DtoMapper dtoMapper;

    @PostMapping(value = "/log-in", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AuthResponseDto login(@RequestBody AuthRequestDto authRequestDto) {
        return dtoMapper.mapJwtTokenToAuthResponseDto(authService.getToken(dtoMapper.mapAuthRequestDtoToUser(authRequestDto)));
    }
}
