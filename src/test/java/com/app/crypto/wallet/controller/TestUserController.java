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
import static org.mockito.Mockito.*;

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
        EditUserDto requestUserDto = new EditUserDto("Alan");
        User userToModify = new User(1L, "jan", "mail@123", true, new ArrayList<>(), new ArrayList<>());
        when(dto.mapToUser(requestUserDto)).thenReturn(userToModify);
        User modifiedUser = new User(1L, "Alan", "mail@123", true, new ArrayList<>(), new ArrayList<>());
        when(service.editUserAccount(userToModify)).thenReturn(modifiedUser);
        ReadUserDto responseUserDto = new ReadUserDto(1L, "Alan", "mail@123", true, new ArrayList<>(), new ArrayList<>());
        when(dto.mapToReadUserDto(modifiedUser)).thenReturn(responseUserDto);
        //When
        ReadUserDto result = userController.editAccount(requestUserDto).getBody();
        //Then
        assertEquals("Alan", result.getUsername());
        verify(service, times(1)).editUserAccount(userToModify);
    }
}
