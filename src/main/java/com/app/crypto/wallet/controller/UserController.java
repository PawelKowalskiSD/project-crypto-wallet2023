package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.domain.dto.EditUserDto;
import com.app.crypto.wallet.domain.dto.ReadUserDto;
import com.app.crypto.wallet.exceptions.UserNotFoundException;
import com.app.crypto.wallet.exceptions.UserPermissionsException;
import com.app.crypto.wallet.mapper.DtoMapper;
import com.app.crypto.wallet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;
    private final DtoMapper dtoMapper;

    @GetMapping
    public ResponseEntity<List<ReadUserDto>> getAllUsers() throws UserPermissionsException {
        return ResponseEntity.ok().body(dtoMapper.mapToReadUserDtoList(userService.getAllUser()));
    }
    @GetMapping(value = "{userId}")
    public ResponseEntity<ReadUserDto> getUser(@PathVariable Long userId) throws UserNotFoundException, UserPermissionsException {
     return ResponseEntity.ok().body(dtoMapper.mapToReadUserDto(userService.getUserById(userId)));
    }
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReadUserDto> editAccount(@RequestBody EditUserDto editUserDto) throws UserNotFoundException, UserPermissionsException {
        return ResponseEntity.ok().body(dtoMapper.mapToReadUserDto(userService.editUserAccount(dtoMapper.mapToUser(editUserDto))));
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) throws UserPermissionsException {
        userService.deleteUserAccount(userId);
        return ResponseEntity.ok().build();
    }
}
