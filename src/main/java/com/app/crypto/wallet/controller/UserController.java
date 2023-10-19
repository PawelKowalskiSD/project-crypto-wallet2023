package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.domain.dto.EditUserDto;
import com.app.crypto.wallet.domain.dto.ReadUserDto;
import com.app.crypto.wallet.exceptions.UserNotFoundException;
import com.app.crypto.wallet.mapper.DtoMapper;
import com.app.crypto.wallet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;
    private final DtoMapper dtoMapper;

    @GetMapping
    public List<ReadUserDto> getAllUsers() {
        return dtoMapper.mapToReadUserDtoList(userService.getAllUser());
    }
    @GetMapping(value = "{userId}")
    public ReadUserDto getUser(@PathVariable Long userId) throws UserNotFoundException {
     return dtoMapper.mapToReadUserDto(userService.getUserById(userId));
    }
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ReadUserDto editAccount(@RequestBody EditUserDto editUserDto) throws UserNotFoundException {
        return dtoMapper.mapToReadUserDto(userService.editUserAccount(dtoMapper.mapToUser(editUserDto)));
    }

    @DeleteMapping
    public void deleteUser() {
        userService.deleteUserAccount();
    }
}
