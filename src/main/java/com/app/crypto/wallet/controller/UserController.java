package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.domain.dto.EditUserDto;
import com.app.crypto.wallet.domain.dto.ReadUserDto;
import com.app.crypto.wallet.domain.dto.ReadWalletDto;
import com.app.crypto.wallet.mapper.DtoMapper;
import com.app.crypto.wallet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;
    private final DtoMapper dtoMapper;


    @PatchMapping(value = "/edit-account")
    public ReadUserDto editAccount(@RequestBody EditUserDto editUserDto) {
        return dtoMapper.mapUserToUserDto(userService.editUserAccount(dtoMapper.mapUserDtoToUser(editUserDto)));
    }

    @DeleteMapping(value = "/delete")
    public void deleteUser() {
        userService.deleteUserAccount();
    }
}
