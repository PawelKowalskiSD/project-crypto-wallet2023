package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.domain.dto.EditUserDto;
import com.app.crypto.wallet.domain.dto.ReadUserDto;
import com.app.crypto.wallet.exceptions.UserNotFoundException;
import com.app.crypto.wallet.mapper.DtoMapper;
import com.app.crypto.wallet.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUserController {


    @Test
    void shouldGetAllUsers() {
        //Given
        UserService service = mock(UserService.class);
        DtoMapper dto = mock(DtoMapper.class);
        UserController userController = new UserController(service, dto);
        List<User> userList = new ArrayList<>();
        List<ReadUserDto> userDtoList = new ArrayList<>();
        userDtoList.add(new ReadUserDto(1L, "jan", "mail@123", true, new ArrayList<>(), new ArrayList<>()));
        userDtoList.add(new ReadUserDto(2L, "Tom", "mail@2p", true, new ArrayList<>(), new ArrayList<>()));
        when(dto.mapToReadUserDtoList(userList)).thenReturn(userDtoList);
        //When
        List<ReadUserDto> result = userController.getAllUsers().getBody();
        //Then
        assertEquals(userDtoList, result);
    }

    @Test
    void shouldGetUser() throws UserNotFoundException {
        //Given
        UserService service = mock(UserService.class);
        DtoMapper dto = mock(DtoMapper.class);
        UserController userController = new UserController(service, dto);
        User user = new User();
        List<ReadUserDto> userDtoList = new ArrayList<>();
        userDtoList.add(new ReadUserDto(1L, "jan", "mail@123", true, new ArrayList<>(), new ArrayList<>()));
        userDtoList.add(new ReadUserDto(2L, "Tom", "mail@2p", true, new ArrayList<>(), new ArrayList<>()));
        when(dto.mapToReadUserDto(user)).thenReturn(userDtoList.get(0));
        when(service.getUserById(userDtoList.get(0).getUserId())).thenReturn(user);
        //When
        ReadUserDto userDto = userController.getUser(1L).getBody();
        System.out.println(userDto);
        System.out.println(userDtoList.get(0));
        //Then
        assertEquals(userDtoList.get(0), userDto);
    }

    @Test
    void shouldEditAccount() throws UserNotFoundException {
        //Given
        UserService service = mock(UserService.class);
        DtoMapper dto = mock(DtoMapper.class);
        UserController userController = new UserController(service, dto);
        User user = new User(/*1L, "jan", "mail@123", true, new ArrayList<>(), new ArrayList<>()*/);
        EditUserDto editUserDto = new EditUserDto("Alan");
        ReadUserDto readUserDto = new ReadUserDto(1L, "jan", "mail@123", true, new ArrayList<>(), new ArrayList<>());
        when(dto.mapToReadUserDto(user)).thenReturn(readUserDto);
        when(service.editUserAccount(dto.mapToUser(editUserDto))).thenReturn(user);
        //When
        ReadUserDto userDto = userController.editAccount(editUserDto).getBody();
        System.out.println(user.getUsername());
        System.out.println(userDto.getUsername());
        System.out.println(readUserDto.getUsername());
        //Then
        assertEquals(editUserDto.getUsername(), userDto.getUsername());
    }
}
