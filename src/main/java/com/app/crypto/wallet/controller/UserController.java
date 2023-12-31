package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.domain.dto.EditUserDto;
import com.app.crypto.wallet.domain.dto.ReadUserDto;
import com.app.crypto.wallet.exceptions.*;
import com.app.crypto.wallet.mapper.DtoMapper;
import com.app.crypto.wallet.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;
    private final DtoMapper dtoMapper;

    @GetMapping
    public ResponseEntity<List<ReadUserDto>> getAllUsers() {
        return ResponseEntity.ok().body(dtoMapper.mapToReadUserDtoList(userService.getAllUser()));
    }
    @GetMapping(value = "{userId}")
    public ResponseEntity<ReadUserDto> getUser(@PathVariable Long userId) throws UserNotFoundException, UserPermissionsException {
     return ResponseEntity.ok().body(dtoMapper.mapToReadUserDto(userService.getUserById(userId)));
    }
    @PatchMapping(value = "/edits", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReadUserDto> editAccount(@RequestBody EditUserDto editUserDto) throws UserNotFoundException, UserPermissionsException, WrongEmailFormatException, DuplicateUsernameException, DuplicateMailAddresseeException {
        return ResponseEntity.ok().body(dtoMapper.mapToReadUserDto(userService.editUserAccount(dtoMapper.mapToUser(editUserDto))));
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) throws UserPermissionsException, UserNotFoundException {
        userService.deleteUserAccount(userId);
        return ResponseEntity.ok().build();
    }
}
