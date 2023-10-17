package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.domain.dto.ReadUserDto;
import com.app.crypto.wallet.mapper.DtoMapper;
import com.app.crypto.wallet.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUserController {

    @Test
    void shouldGetAllUsers() {
        //given
        UserService service = mock(UserService.class);
        DtoMapper dto = mock(DtoMapper.class);
        UserController userController = new UserController(service, dto);
        List<User> userList = new ArrayList<>();
        List<ReadUserDto> userDtoList = new ArrayList<>();
        userDtoList.add(new ReadUserDto(1L, "jan", "mail@123", true, new ArrayList<>(), new ArrayList<>()));
        userDtoList.add(new ReadUserDto(2L, "Tom", "mail@2p", true, new ArrayList<>(), new ArrayList<>()));
        when(dto.mapToReadUserDtoList(userList)).thenReturn(userDtoList);
        //When
        List<ReadUserDto> result = userController.getAllUsers();
        //Then
        assertEquals(userDtoList, result);
    }
}
