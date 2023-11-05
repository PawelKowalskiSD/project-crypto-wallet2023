package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.domain.dto.EditUserDto;
import com.app.crypto.wallet.domain.dto.ReadUserDto;
import com.app.crypto.wallet.exceptions.UserNotFoundException;
import com.app.crypto.wallet.exceptions.UserPermissionsException;
import com.app.crypto.wallet.mapper.DtoMapper;
import com.app.crypto.wallet.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TestUserController {
    private final UserService service = mock(UserService.class);
    private final DtoMapper dto = mock(DtoMapper.class);


    @Test
    void shouldGetAllUsers() {
        //Given
        UserController userController = new UserController(service, dto);
        List<User> databaseUsers = new ArrayList<>();
        databaseUsers.add(new User(1L, "jan", "mail@123", true, new ArrayList<>(), new ArrayList<>()));
        databaseUsers.add(new User(2L, "Tom", "mail@2p", true, new ArrayList<>(), new ArrayList<>()));
        when(service.getAllUser()).thenReturn(databaseUsers);
        List<ReadUserDto> responseUsersDto = new ArrayList<>();
        responseUsersDto.add(new ReadUserDto(1L, "jan", "mail@123", true, new ArrayList<>(), new ArrayList<>()));
        responseUsersDto.add(new ReadUserDto(2L, "Tom", "mail@2p", true, new ArrayList<>(), new ArrayList<>()));
        when(dto.mapToReadUserDtoList(databaseUsers)).thenReturn(responseUsersDto);
        //When
        List<ReadUserDto> result = userController.getAllUsers().getBody();
        //Then
        assertEquals(2, result.size());
        verify(service, times(1)).getAllUser();
    }

    @Test
    void shouldGetUser() throws UserNotFoundException, UserPermissionsException {
        //Given
        UserController userController = new UserController(service, dto);
        User databaseJan = new User(1L, "jan", "mail@123", true, new ArrayList<>(), new ArrayList<>());
        Long requestUserId = 1L;
        when(service.getUserById(requestUserId)).thenReturn(databaseJan);
        ReadUserDto responseUserDto = new ReadUserDto(1L, "jan", "mail@123", true, new ArrayList<>(), new ArrayList<>());
        when(dto.mapToReadUserDto(databaseJan)).thenReturn(responseUserDto);
        //When
        ReadUserDto result = userController.getUser(requestUserId).getBody();
        //Then
        assertEquals(1L, result.getUserId());
        verify(service, times(1)).getUserById(requestUserId);
    }

    @Test
    void shouldEditAccount() throws UserNotFoundException, UserPermissionsException {
        //Given
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

    @Test
    void shouldDeleteUser() throws UserPermissionsException {
        //Given
        UserController userController = new UserController(service, dto);
        User databaseJan = new User(1L, "jan", "mail@123", true, new ArrayList<>(), new ArrayList<>());
        long requestUserId = 1L;
        //When
        userController.deleteUser(requestUserId);
        //Then
        verify(service, times(1)).deleteUserAccount(databaseJan.getUserId());
    }
}
